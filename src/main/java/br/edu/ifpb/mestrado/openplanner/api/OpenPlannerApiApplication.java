package br.edu.ifpb.mestrado.openplanner.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OpenPlannerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenPlannerApiApplication.class, args);
    }

}
