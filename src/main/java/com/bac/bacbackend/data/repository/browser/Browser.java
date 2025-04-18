package com.bac.bacbackend.data.repository.browser;

import org.openqa.selenium.WebDriver;

public interface Browser {
    WebDriver create();
    void start(String s);
    void stop();
    WebDriver getDriver();
}
