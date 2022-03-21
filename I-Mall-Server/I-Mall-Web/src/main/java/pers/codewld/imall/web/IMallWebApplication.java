package pers.codewld.imall.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"pers.codewld.imall"})
public class IMallWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMallWebApplication.class, args);
    }

}
