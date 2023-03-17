package com.example.urlShortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("application.properties")
public class ShortsLinkProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortsLinkProjectApplication.class, args);
    }

}
