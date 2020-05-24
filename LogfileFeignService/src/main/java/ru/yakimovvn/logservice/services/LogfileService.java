package ru.yakimovvn.logservice.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
public class LogfileService {

    private final Resource logfile = new ClassPathResource("logs/logfile.log");

    public byte[] getLogFile() throws IOException {

        File file = logfile.getFile();
        return Files.readAllBytes(file.toPath());

    }
}
