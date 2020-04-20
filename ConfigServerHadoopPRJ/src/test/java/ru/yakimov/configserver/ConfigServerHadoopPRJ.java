package ru.yakimov.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@SpringBootApplication
@EnableConfigServer
public class ConfigServerHadoopPRJ {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerHadoopPRJ.class, args);
    }
}
