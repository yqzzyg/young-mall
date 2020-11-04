package com.young.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.young.db","com.young.mall"})
public class YoungAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoungAdminApplication.class, args);
    }

}
