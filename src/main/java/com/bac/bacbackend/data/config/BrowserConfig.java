package com.bac.bacbackend.data.config;

import com.bac.bacbackend.data.model.browser.ChromeBrowser;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(ChromeBrowser.class)
@Configuration
public class BrowserConfig {

}
