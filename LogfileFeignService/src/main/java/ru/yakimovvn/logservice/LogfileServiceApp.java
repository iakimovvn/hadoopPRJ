package ru.yakimovvn.logservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@SpringBootApplication
@EnableEurekaClient
public class LogfileServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(LogfileServiceApp.class, args);
    }
}
