package com.babyblackdog.ddogdog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DdogdogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdogdogApplication.class, args);
    }

}
