package io.rac.shortener.job;

import io.rac.shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlCleanupJob {
    public final static String JOB_NAME = "url-cleanup-job_cleanup-expired-urls";

    private final ShortUrlRepository repository;

    @Scheduled(cron = "0 * * * * *")
    @SchedulerLock(
            name = JOB_NAME,
            lockAtLeastFor = "PT5M",
            lockAtMostFor = "PT15M"
    )
//    @SentryCheckIn("url-cleanup-job")
    @Transactional
    public void cleanupExpiredUrls() {
        log.info("Running scheduled '{}' to clean up expired URLs...", JOB_NAME);

        var currentDateTime = LocalDateTime.now();
        repository.deleteByExpiresAtBefore(currentDateTime);
        
        log.info("scheduled job '{}' was finished with success.", JOB_NAME);
    }
}
