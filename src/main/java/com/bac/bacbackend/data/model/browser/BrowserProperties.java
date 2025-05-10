package com.bac.bacbackend.data.model.browser;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Record class for the creation of each Chrome Browser. Alias and driver path exist in a .yaml file.
 *
 * @param driverPath path of where to find the driver
 * @param headless deciding factor if the browser is to be headless or not
 * @param alias alias used for ID'ing itself when scraping
 */
@ConfigurationProperties(prefix = "browser.chrome")
public record BrowserProperties(

        String driverPath,
        boolean headless,
        String alias

){}


