package ru.yakimovvn.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerMain {
    public static void main(String[] args) {
        new SpringApplication(EurekaServerMain.class).run(args);
    }
}
