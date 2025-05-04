package com.bac.bacbackend.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration for threading, has to be here to allow the async annotation
 */
@Configuration
@EnableAsync
public class AsyncConfig {}
