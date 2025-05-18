package com.bac.bacbackend.data.repository.automation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Instant;

/**
 * Simple listener class to catch any changes from postgres. Runs on a fixed time and will save the last value for
 * update in memory as a reference point. Will therefor always run when application is started (as memory is empty)
 * Does not use a traditional message broker, mostly because it would be overkill for this use case and somewhat
 * because the application is mainly centered around using Redis as the primary database.
 */
@Component
public class UpdateScraperProperties {
    private final JdbcTemplate jdbcTemplate;
    private final ScraperObjectRepo scraperObjectRepo;
    private static Instant lastUpdate = Instant.EPOCH;

    public UpdateScraperProperties(JdbcTemplate jdbcTemplate, ScraperObjectRepo scraperObjectRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.scraperObjectRepo = scraperObjectRepo;
    }

    /**
     * Change preferred scheduled rate with changing the fixedRate annotation (time is in m/s, 5000 = 5 seconds)
     * It sends a message on the preferred rate to the database, checks for the time-column. Updates the
     * scraping properties to Redis if time in db are more recent than what is stored in memory.
     */
    @Scheduled(fixedRate = 5000)
    public void checkForUpdates() {
        Instant latestUpdate = jdbcTemplate.queryForObject(
                "SELECT MAX(updated_at) FROM scraper_objects", Instant.class);
        if(latestUpdate != null && latestUpdate.isAfter(lastUpdate)) {
            lastUpdate = latestUpdate;
            scraperObjectRepo.cache();
            System.out.println("Scraper properties has been changed, caching to redis. " + lastUpdate);
        }
    }
}