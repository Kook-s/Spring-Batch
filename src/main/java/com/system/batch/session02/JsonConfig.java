package com.system.batch.session02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.converter.JsonJobParametersConverter;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class JsonConfig {

    @Bean
    public JobParametersConverter jobParametersConverter() {
        return new JsonJobParametersConverter();
    }

    @Bean
    public Job parametersJob(JobRepository jobRepository, Step parametersStep) {
        return new JobBuilder("parametersJob", jobRepository)
                .start(parametersStep)
                .build();
    }

    @Bean
    public Step parametersStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, Tasklet parametersTasklet) {
        return new StepBuilder("parametersStep", jobRepository)
                .tasklet(parametersTasklet, transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet parametersTasklet(
            @Value("#{jobParameters['infiltrationTargets']}") String infiltrationTargets
    ) {
        return (contribution, chunkContext) -> {
            String[] targets = infiltrationTargets.split(",");

            log.info("⚡ 침투 작전 개시");
            log.info("첫 번째 타겟: {} 침투 시작", targets[0]);
            log.info("마지막 타겟: {} 에서 집결", targets[1]);
            log.info("🎯 임무 전달 완료");

            return RepeatStatus.FINISHED;
        };
    }

}
//./gradlew bootRun --args="--spring.batch.job.name=parametersJob infiltrationTargets='{\"value\":\"판교서버실,안산데이터센터\",\"type\": \"java.lang.String\"}'"