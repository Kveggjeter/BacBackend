package com.bac.bacbackend.domain.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public class BrowserSettings {

    private final ScraperProps props;

    public BrowserSettings(ScraperProps props) {
        this.props = props;
        System.setProperty("webdriver.chrome.driver", props.getChromedriverPath());
    }

    public WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        if (props.isHeadless()) options.addArguments("--headless=new");
        options.addArguments("user-agent=" + props.getUserAgent());
        return new ChromeDriver(options);
    }

}
