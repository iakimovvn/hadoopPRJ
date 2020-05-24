package ru.yakimovvn.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Slf4j
@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping
    public String list (){
        return "admin";
    }
}
