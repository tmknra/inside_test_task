package com.example.inside_test_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.inside_test_task.repository")
public class InsideTestTaskApp {

    public static void main(String[] args) {
        SpringApplication.run(InsideTestTaskApp.class, args);
    }

}
