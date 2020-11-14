package com.young.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.young.db","com.young.mall"})
public class YoungWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoungWxApiApplication.class, args);
    }

}
