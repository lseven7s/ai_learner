package com.ai.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ai.learning.mapper")
@EnableScheduling
public class AiLearningHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiLearningHelperApplication.class, args);
    }

}
