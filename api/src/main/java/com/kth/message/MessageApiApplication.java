package com.kth.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@ConfigurationPropertiesScan
@EntityScan(basePackages = {"com.kth.message.entity"})
@SpringBootApplication(scanBasePackages = {"com.kth"})
public class MessageApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageApiApplication.class, args);
    }
}
