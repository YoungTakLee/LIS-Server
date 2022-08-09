package com.green.config;

import com.green.repository.CmsUserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataBaseBackUpConfig {
    private final CmsUserRepository cmsUserRepository;

    DataBaseBackUpConfig(CmsUserRepository cmsUserRepository) {
        this.cmsUserRepository = cmsUserRepository;
    }
    @Scheduled(cron = " 0 */30 * * * *") //30분 주기로 DB 백업
    void dbBackUp() {
        LocalDate localDate = LocalDate.now();
        cmsUserRepository.backUpQuery(localDate.toString());
    }
}
