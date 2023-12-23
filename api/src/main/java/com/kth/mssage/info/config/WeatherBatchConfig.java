package com.kth.mssage.info.config;

import com.kth.mssage.repository.local.Geometry;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class WeatherBatchConfig {

    private final CSVReader reader;
    private final CSVWriter writer;

    @Bean
    public Job weatherJob(JobRepository jobRepository, Step weatherCSVFileReadStep) {
        return new JobBuilder("weatherJob", jobRepository)
                .start(weatherCSVFileReadStep)
                .build();
    }

    @Bean
    public Step weatherCSVFileReadStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("weatherCsvFileReadStep", jobRepository)
                .<Geometry, Geometry>chunk(1000, transactionManager)
                .reader(reader.csvScheduleReader())
                .writer(writer)
                .build();
    }

}
