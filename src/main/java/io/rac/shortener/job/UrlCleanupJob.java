package io.rac.shortener.job;

import io.rac.shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlCleanupJob {

    private final ShortUrlRepository repository;

    @Scheduled(cron = "0 * * * * *")
    @SchedulerLock(
            name = "url-cleanup-job_cleanup-expired-urls",
            lockAtLeastFor = "PT5M",
            lockAtMostFor = "PT15M"
    )
//    @SentryCheckIn("url-cleanup-job")
    public void cleanupExpiredUrls() {
        log.info("Running scheduled job to clean up expired URLs...");
        var currentDateTime = LocalDateTime.now();
        repository.deleteByExpiresAtBefore(currentDateTime);
        log.info("Cleanup job finished.");
    }
}
