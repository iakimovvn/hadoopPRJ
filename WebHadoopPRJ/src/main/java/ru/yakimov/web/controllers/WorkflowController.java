package ru.yakimov.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yakimov.web.persistence.entities.Workflow;
import ru.yakimov.web.persistence.repositories.UserRepository;
import ru.yakimov.web.services.LogfileService;
import ru.yakimov.web.services.TypeService;
import ru.yakimov.web.services.WorkflowService;

import java.security.Principal;
import java.util.Date;
import java.util.UUID;

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
}
