package ru.yakimovvn.web.controllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yakimovvn.web.persistence.repositories.UserRepository;
import ru.yakimovvn.web.services.rabbitmq.RabbitMqService;
import ru.yakimovvn.web.persistence.entities.Wfl_logfile;
import ru.yakimovvn.web.persistence.entities.Wfl_type;
import ru.yakimovvn.web.persistence.entities.Workflow;
import ru.yakimovvn.web.services.*;
import ru.yakimovvn.web.validator.WorkflowValidator;

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
@RequiredArgsConstructor
@RequestMapping(value = "/workflow")
//@Api("Контноллер работы с рабочими процессами.")
public class WorkflowController {

    private final WorkflowService workflowService;
    private final TypeService typeService;
    private final UserRepository userRepository;
    private final LogfileService logfileService;
    private final ConfigService configService;
    private final DirectoryService directoryService;

    private final WorkflowValidator workflowValidator;

    private final RabbitMqService rabbitMqService;



    @GetMapping
//    @ApiOperation(value = "Отображение списака рабочих процессов юзера")
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
//    @ApiOperation(value = "Отображение рабочего процесса")
    public String show(@PathVariable("uuid")UUID uuid, Model model){
        Workflow workflow = workflowService.getByUuid(uuid);
        model.addAttribute("workflow", workflow);
        model.addAttribute("logsList", logfileService.findAllByWorkflow(workflow) );
        return "showWf";
    }

    @GetMapping(value = "/edit/{uuid}")
//    @ApiOperation(value = "Показать страницу редактирования процесса")
    public String update(@PathVariable("uuid")UUID uuid, Model model){
        Workflow workflow = workflowService.getByUuid(uuid);
        model.addAttribute("workflow",  workflowService.getByUuid(uuid));
        model.addAttribute("types", typeService.getAllWithOutOne(workflow.getWfl_type()));
        return "updateWf";
    }

    @GetMapping(value = "/delete/{uuid}")
//    @ApiOperation(value = "Удаление процесса")
    public String delete(@PathVariable("uuid")UUID uuid){
        Workflow workflow = workflowService.getByUuid(uuid);
        workflow.setDeleted(true);
        workflowService.save(workflow);
        return "redirect:/workflow";

    }

    @PostMapping
//    @ApiOperation(value = "Сохраннеие  рабочего процесса")
    public String saveWorkflow(Workflow workflow,  BindingResult bindingResult, Model model) {

        workflowValidator.validate(workflow, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("workflow",  workflow);
            model.addAttribute("types", typeService.getAllWithOutOne(workflow.getWfl_type()));
            return "updateWf";

        }
        workflow = workflowService.save(workflow);
        return "redirect:/workflow/" + workflow.getUuid();
    }


    @GetMapping("/new")
//    @ApiOperation(value = "Показать страницу создания нового процесса")
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
//    @ApiOperation(value = "Запуск рабочего процесса")
    public String run(@PathVariable("uuid")UUID uuid){
        Workflow workflow = workflowService.getByUuid(uuid);

        Wfl_logfile newLog = logfileService.createNewLogfile(workflow);

        rabbitMqService.sendMessage(
                workflowService.workflowToPojo(workflow, newLog.getUuid().toString()),
                workflow.getWfl_type().getTitle()
        );

        workflow.setLast_run_date(new Date());
        workflow.setRun(true);
        workflowService.save(workflow);

       return "redirect:/workflow";

    }
}
