package ru.yakimovvn.soupHdfs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */


@SpringBootApplication(scanBasePackages = "ru.yakimovvn.soupHdfs")
public class SpringHDFSSoupService {
    public static void main(String[] args) {
        SpringApplication.run(SpringHDFSSoupService.class, args);

    }
}
