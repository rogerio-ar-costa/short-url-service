package io.rac.shortener;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@SpringBootApplication
public class ShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortenerApplication.class, args);
	}

}
