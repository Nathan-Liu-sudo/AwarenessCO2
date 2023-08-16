package com.tp11.carbontracker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tp11.carbontracker.mapper")
public class CarbonTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonTrackerApplication.class, args);
    }

}
