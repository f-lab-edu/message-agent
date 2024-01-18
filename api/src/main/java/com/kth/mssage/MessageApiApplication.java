package com.kth.mssage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@ConfigurationPropertiesScan
@EnableFeignClients
@EntityScan(basePackages = {"com.kth.mssage.repository"})
@EnableJpaRepositories(basePackages = "com.kth.mssage.repository")
@SpringBootApplication(scanBasePackages = {"com.kth"})
public class MessageApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageApiApplication.class, args);
    }
}
