package com.system.batch.session02;

import lombok.Data;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@StepScope
@Component
public class SystemInfiltrationParameters {

    // POJO를 활용한 Job 파라미터 주입

    @Value("#{jobParameters[missionName]}")
    private String missionName;
    private int securityLevel;
    private final String operationCommander;

    public SystemInfiltrationParameters(@Value("#{jobParameters[operationCommander]}") String operationCommander) {
        this.operationCommander = "commander";
    }

    @Value("#{jobParameters[securityLavel]}")
    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }
}

/*
@Bean
public Tasklet terminatorTasklet(SystemInfiltrationParameters infiltrationParams) {
    return (contribution, chunkContext) -> {
        log.info("⚔️ 시스템 침투 작전 초기화!");
        log.info("임무 코드네임: {}", infiltrationParams.getMissionName());
        log.info("보안 레벨: {}", infiltrationParams.getSecurityLevel());
        log.info("작전 지휘관: {}", infiltrationParams.getOperationCommander());

        // 보안 레벨에 따른 침투 난이도 계산
        int baseInfiltrationTime = 60; // 기본 침투 시간 (분)
        int infiltrationMultiplier = switch (infiltrationParams.getSecurityLevel()) {
            case 1 -> 1; // 저보안
            case 2 -> 2; // 중보안
            case 3 -> 4; // 고보안
            case 4 -> 8; // 최고 보안
            default -> 1;
        };

        int totalInfiltrationTime = baseInfiltrationTime * infiltrationMultiplier;

        log.info("💥 시스템 해킹 난이도 분석 중...");
        log.info("🕒 예상 침투 시간: {}분", totalInfiltrationTime);
        log.info("🏆 시스템 장악 준비 완료!");

        return RepeatStatus.FINISHED;
    };
}

./gradlew bootRun --args='--spring.batch.job.name=terminatorJob
missionName=안산_데이터센터_침투,java.lang.String
operationCommander=KILL-9
securityLevel=3,java.lang.Integer,false'
*/
