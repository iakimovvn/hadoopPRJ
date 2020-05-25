package ru.yakimovvn.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yakimovvn.web.services.LogfileService;
import ru.yakimovvn.web.services.UserService;

import java.security.Principal;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Slf4j
@Controller
@RequestMapping(value = "/log")
@RequiredArgsConstructor
public class LogController {

    private final LogfileService logfileService;
    private final UserService userService;

    @GetMapping
    public String logfile(@RequestParam String path, @RequestParam String workflowUUID, Model model, Principal principal){

        String folder = userService.getLogFolderByLogin(principal.getName());

        model.addAttribute("workflowUUID", workflowUUID);
        model.addAttribute("logfilePath", path);
        model.addAttribute("logfile", logfileService.getLogfile(folder+path));
        return "showLog";
    }
}
