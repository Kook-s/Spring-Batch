package com.system.batch.session01;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

import java.io.File;

@Slf4j
public class DeleteOldFilesTasklet implements Tasklet {
    private final String path;
    private final int daysOld;

    public DeleteOldFilesTasklet(String path, int daysOld) {
        this.path = path;
        this.daysOld = daysOld;
    }

    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File dir = new File(path);
        long cutoffTime = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L);

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.lastModified() < cutoffTime) {
                    if (file.delete()) {
                        log.info("🔥 파일 삭제: {}", file.getName());
                    } else {
                        log.info("⚠️  파일 삭제 실패: {}", file.getName());
                    }
                }
            }
        }
        return RepeatStatus.FINISHED;
    }
}

//// FileCleanupBatchConfig
//@Bean
//public Tasklet deleteOldFilesTasklet() {
//    // "temp" 디렉토리에서 30일 이상 지난 파일 삭제
//    return new DeleteOldFilesTasklet("/path/to/temp", 30);
//}
//
//@Bean
//public Step deleteOldFilesStep() {
//    return new StepBuilder("deleteOldFilesStep", jobRepository)
//            .tasklet(deleteOldFilesTasklet(), transactionManager)
//            .build();
//}
//
//@Bean
//public Job deleteOldFilesJob() {
//    return new JobBuilder("deleteOldFilesJob", jobRepository)
//            .start(deleteOldFilesStep())
//            .build();
//}
