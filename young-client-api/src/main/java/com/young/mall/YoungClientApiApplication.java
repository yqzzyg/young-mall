package com.young.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.young.db","com.young.mall"})
public class YoungClientApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoungClientApiApplication.class, args);
    }

}
