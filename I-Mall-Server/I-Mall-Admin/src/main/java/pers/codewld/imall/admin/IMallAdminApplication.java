package pers.codewld.imall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"pers.codewld.imall"})
public class IMallAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMallAdminApplication.class, args);
    }

}
