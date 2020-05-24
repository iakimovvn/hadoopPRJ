package ru.yakimovvn.logservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
public class LogfileService {

    @Value("${logservice.data.path}")
    private String dataPath;

//    private final Resource logfile = new ClassPathResource("logs/logfile.log");

    public byte[] getLogFile(String path) throws IOException {
//         Resource logfile = new ClassPathResource(dataPath+"/"+path);
        String filePath = dataPath+"/"+path;
        return Files.readAllBytes(Paths.get(filePath));

    }
}
