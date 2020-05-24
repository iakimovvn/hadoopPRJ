package ru.yakimovvn.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yakimovvn.web.services.UserService;
import ru.yakimovvn.web.services.soap.HDFSService;

import java.security.Principal;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */
@Slf4j
@Controller
@RequestMapping(value = "/hdfs")
@RequiredArgsConstructor
public class HDFSController {

    private final HDFSService hdfsService;
    private final UserService userService;

    @GetMapping
    public String list(
            Model model,
            Principal principal,
            @RequestParam(required = false) String path
    ) throws Exception {
        String userPath = userService.getHdfsFolderByLogin(principal.getName());
        if(path == null){
            path = userPath;
        }

        if(!path.startsWith(userPath)){
            throw new Exception("Folder not user");
        }

        model.addAttribute("path", path);
        model.addAttribute("items", hdfsService.listFolder(path));

        return "hdfs";
    }

}
