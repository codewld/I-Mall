package pers.codewld.imall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "pers.codewld.imall.*.repository")
@SpringBootApplication(scanBasePackages = {"pers.codewld.imall"})
public class IMallAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMallAdminApplication.class, args);
    }

}
