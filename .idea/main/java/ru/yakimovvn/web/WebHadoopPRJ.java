package ru.yakimovvn.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@EnableFeignClients
@SpringBootApplication
public class WebHadoopPRJ {
    public static void main(String[] args) {
        SpringApplication.run(WebHadoopPRJ.class, args);
    }
}
