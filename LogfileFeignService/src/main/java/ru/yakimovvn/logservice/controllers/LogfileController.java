package ru.yakimovvn.logservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yakimovvn.logservice.services.LogfileService;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@RestController
@RequestMapping("/logfiles")
@RequiredArgsConstructor
public class LogfileController {

    public final LogfileService logfileService;

    @GetMapping ("/get")
    public ResponseEntity<byte[]> getLog(@RequestParam String path) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(logfileService.getLogFile(path), headers, HttpStatus.OK);
    }

    @GetMapping ("/create")
    public ResponseEntity<String> createLog(@RequestParam String path){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(logfileService.createLog(path), headers, HttpStatus.OK);
    }
}
