package com.example.inside_test_task;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {
        InsideTestTaskApp.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class InsideTestTaskAppTests {

    @Test
    void contextLoads() {
    }

}
