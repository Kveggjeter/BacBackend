package com.bac.bacbackend.data.repository.browser;

import org.openqa.selenium.WebDriver;

/**
 * Interface used on the data side of the application
 */
public interface Browser {
    WebDriver create();
    void start(String s);
    void stop();
    WebDriver getDriver();
}
