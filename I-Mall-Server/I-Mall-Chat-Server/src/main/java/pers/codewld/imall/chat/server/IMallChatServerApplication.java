package pers.codewld.imall.chat.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"pers.codewld.imall"})
public class IMallChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMallChatServerApplication.class, args);
    }

}
