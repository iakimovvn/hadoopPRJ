package ru.yakimov.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yakimov.web.persistence.entities.Wfl_config;
import ru.yakimov.web.persistence.entities.Wfl_type;
import ru.yakimov.web.persistence.entities.Workflow;
import ru.yakimov.web.persistence.repositories.UserRepository;
import ru.yakimov.web.services.*;

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
                type
                )
        );
        model.addAttribute("types", typeService.findAll());
        return "workflows";
    }


    @GetMapping(value = "/{uuid}")
    public String show(@PathVariable("uuid")UUID uuid, Model model){
        Workflow workflow = workflowService.getById(uuid);
        model.addAttribute("workflow", workflow);
        model.addAttribute("logsList", logfileService.findAllByWorkflow(workflow) );
        return "showWf";
    }

    @GetMapping(value = "/edit/{uuid}")
    public String update(@PathVariable("uuid")UUID uuid, Model model){
        Workflow workflow = workflowService.getById(uuid);
        model.addAttribute("workflow",  workflowService.getById(uuid));
        List<Wfl_type> types = typeService.findAll()
                .stream()
                .filter(v -> !v.getId().equals(workflow.getWfl_type().getId()))
                .collect(Collectors.toList());
        model.addAttribute("types", types);
        return "updateWf";
    }

    @PostMapping
    public String saveWorkflow(@Valid Workflow workflow) {
        Wfl_config config = workflow.getWfl_config();
        directoryService.save(config.getWfl_directory_to());
        directoryService.saveAll(config.getWfl_directories_from());
//        configService.save(workflow.getWfl_config());
        workflow = workflowService.save(workflow);
        return "redirect:/workflow/" + workflow.getId();
    }
}
