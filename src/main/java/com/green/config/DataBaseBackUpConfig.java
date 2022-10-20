package com.green.config;

import com.green.repository.CmsUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataBaseBackUpConfig {
    private final CmsUserRepository cmsUserRepository;

    @Scheduled(cron = " 0 0 0/1 * * *")
        //30분 주기로 DB 백업
    void dbBackUp() {
        cmsUserRepository.backUpQuery();
        Path relativePath = Paths.get("");
        String path = relativePath.toAbsolutePath().toString();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.getName().contains("backup.zip")) {
                    try {
                        LocalDateTime dateTime = LocalDateTime.ofInstant(Files.readAttributes(Paths.get(relativePath + f.getName()), BasicFileAttributes.class).creationTime().toInstant(),ZoneId.of("Asia/Seoul"));
                        LocalDateTime now = LocalDateTime.now();
                        if(now.minusDays(30).isAfter(dateTime)) {
                            f.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
