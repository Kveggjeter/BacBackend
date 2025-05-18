package com.bac.bacbackend.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        com.bac.bacbackend.data.repository.automation.ScraperDataObjectRepo.class
})
@EnableRedisRepositories(basePackageClasses = {
        com.bac.bacbackend.data.repository.article.ArticleDataRepo.class,
        com.bac.bacbackend.data.repository.article.FailedDataRepo.class,
        com.bac.bacbackend.data.repository.scraper.NewsParamDataRepo.class
})
public class RepositoryConfig {
}