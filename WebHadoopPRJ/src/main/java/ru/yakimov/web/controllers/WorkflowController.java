package ru.yakimov.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yakimov.web.persistence.entities.*;
import ru.yakimov.web.persistence.repositories.UserRepository;
import ru.yakimov.web.services.*;
import ru.yakimov.web.utils.JsonConverter;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
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
    private final AmqpTemplate template;
    private final JsonConverter jsonConverter;


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
        String workflowPojoJson = jsonConverter.objectToJsonString(
                workflowService.workflowToPojo(workflow)
        );

        template.convertAndSend("hadoop-prj.exchange","hadoop.prj", workflowPojoJson);
        return "redirect:/workflow";

    }
}
