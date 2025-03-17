package org.project.pals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PalsAndPlansApplication {

    public static void main(String[] args) {
        SpringApplication.run(PalsAndPlansApplication.class, args);
    }

}
