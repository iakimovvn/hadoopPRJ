package ru.yakimovvn.soupHdfs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "ru.yakimovvn.soupHdfs")
public class SpringHDFSSoupService {
    public static void main(String[] args) {
        SpringApplication.run(SpringHDFSSoupService.class, args);

    }
}
