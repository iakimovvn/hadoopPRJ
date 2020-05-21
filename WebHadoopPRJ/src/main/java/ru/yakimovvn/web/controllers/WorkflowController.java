package ru.yakimovvn.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yakimov.web.persistence.entities.*;
import ru.yakimovvn.web.persistence.repositories.UserRepository;
import ru.yakimov.web.services.*;
import ru.yakimovvn.web.services.rabbitmq.RabbitMqService;
import ru.yakimovvn.web.persistence.entities.Wfl_logfile;
import ru.yakimovvn.web.persistence.entities.Wfl_type;
import ru.yakimovvn.web.persistence.entities.Workflow;
import ru.yakimovvn.web.services.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Slf4j
@Controller
@RequestMapping(value = "/workflow")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowService workflowService;
    private final TypeService typeService;
    private final UserRepository userRepository;
    private final LogfileService logfileService;
    private final ConfigService configService;
    private final DirectoryService directoryService;

    private final RabbitMqService rabbitMqService;



    @GetMapping
    public String list(
            Model model,
            Principal principal,
            @RequestParam(required = false) Date createDateFrom,
            @RequestParam(required = false) Date createDateTo,
            @RequestParam(required = false) Date lastRunFrom,
            @RequestParam(required = false) Date lastDateTo,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String type
                       ) {
        model.addAttribute("workflows", workflowService.findAll(
                userRepository.findOneByLogin(principal.getName()),
                createDateFrom,
                createDateTo,
                lastRunFrom,
                lastDateTo,
                title,
                type,
                false
                )
        );
        model.addAttribute("types", typeService.findAll());
        return "workflows";
    }


    @GetMapping(value = "/{uuid}")
    public String show(@PathVariable("uuid")UUID uuid, Model model){
        Workflow workflow = workflowService.getByUuid(uuid);
        model.addAttribute("workflow", workflow);
        model.addAttribute("logsList", logfileService.findAllByWorkflow(workflow) );
        return "showWf";
    }

    @GetMapping(value = "/edit/{uuid}")
    public String update(@PathVariable("uuid")UUID uuid, Model model){
        Workflow workflow = workflowService.getByUuid(uuid);
        model.addAttribute("workflow",  workflowService.getByUuid(uuid));
        List<Wfl_type> types = typeService.findAll()
                .stream()
                .filter(v -> !v.getUuid().equals(workflow.getWfl_type().getUuid()))
                .collect(Collectors.toList());
        model.addAttribute("types", types);
        return "updateWf";
    }

    @GetMapping(value = "/delete/{uuid}")
    public String delete(@PathVariable("uuid")UUID uuid){
        Workflow workflow = workflowService.getByUuid(uuid);
        workflow.setDeleted(true);
        workflowService.save(workflow);
        return "redirect:/workflow";

    }

    @PostMapping
    public String saveWorkflow(@Valid Workflow workflow) {
        workflow = workflowService.save(workflow);
        return "redirect:/workflow/" + workflow.getUuid();
    }


    @GetMapping("/new")
    public String newWorkflow(Model model, Principal principal){

        model.addAttribute("workflow",
                workflowService.getCleanWorkflow(
                        userRepository.findOneByLogin(
                                principal.getName())
                )
        );
        model.addAttribute("types", typeService.findAll());

        return "updateWf";
    }

    @GetMapping (value = "/run/{uuid}")
    public String run(@PathVariable("uuid")UUID uuid){
        Workflow workflow = workflowService.getByUuid(uuid);

        Wfl_logfile newLog = logfileService.createNewLogfile(workflow);

        rabbitMqService.sendMessage(
                workflowService.workflowToPojo(workflow, newLog.getUuid().toString()),
                workflow.getWfl_type().getTitle()
        );

        workflow.setRun(true);
        workflowService.save(workflow);

       return "redirect:/workflow";

    }
}
