package io.rac.shortener.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Force Flyway migration on startup
 *
 */
@Configuration
public class FlywayConfig {

  @Bean(initMethod = "migrate")
  public Flyway flyway(DataSource dataSource) {
    return Flyway.configure()
        .dataSource(dataSource)
        .locations("classpath:db/migration")
        .baselineOnMigrate(true)
        .validateOnMigrate(true)
        .failOnMissingLocations(true)
        .outOfOrder(true)
        .load();
  }
}
