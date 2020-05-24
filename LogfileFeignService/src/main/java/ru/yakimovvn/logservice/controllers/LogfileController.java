package ru.yakimovvn.logservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakimovvn.logservice.services.LogfileService;

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

    @GetMapping("/logfile")
    public ResponseEntity<byte[]> getLogfile() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(logfileService.getLogFile(), headers, HttpStatus.OK);
    }
}
