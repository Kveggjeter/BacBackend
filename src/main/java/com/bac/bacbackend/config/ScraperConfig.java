package com.bac.bacbackend.config;

import com.bac.bacbackend.domain.service.ScraperProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(ScraperProps.class)
@Configuration
public class ScraperConfig {}
