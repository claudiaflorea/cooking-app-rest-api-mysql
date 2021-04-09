package com.practice.cooking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookingApplication.class, args);

        log.info("********************************************\"" +
            "RUNNING\"" +
            "********************************************");
    }

}
