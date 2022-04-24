package com.autohome.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(BeApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
