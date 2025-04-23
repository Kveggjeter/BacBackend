package com.bac.bacbackend.domain.port;

import org.openqa.selenium.WebDriver;

public interface IChrome {
    void start(String s);
    void stop();
    WebDriver getDriver();
}
