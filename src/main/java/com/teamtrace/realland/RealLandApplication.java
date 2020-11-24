package com.teamtrace.realland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.teamtrace.realland")
public class RealLandApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealLandApplication.class, args);
    }

}
