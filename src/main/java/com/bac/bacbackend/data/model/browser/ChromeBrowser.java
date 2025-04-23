package com.bac.bacbackend.data.model.browser;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "browser.chrome")
public record ChromeBrowser(

        String driverPath,
        boolean headless,
        String alias

){}


