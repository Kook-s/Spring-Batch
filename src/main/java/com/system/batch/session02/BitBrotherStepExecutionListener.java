package com.system.batch.session02;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BitBrotherStepExecutionListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step 구역 감시 시작. 모든 행동이 기록된다.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step 감시 종료. 모든 행동이 기록되었다.");
        log.info("Big Brother의 감시망에서 벗어날 수 없을 것이다.");
        return ExitStatus.COMPLETED;
    }
}
