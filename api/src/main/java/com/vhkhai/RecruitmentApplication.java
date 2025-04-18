package com.vhkhai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties
@EnableAsync
@SpringBootApplication
public class RecruitmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecruitmentApplication.class, args);
    }
}
