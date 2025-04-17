package com.bac.bacbackend.data.scraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserSettings {

    private static final String path = "C:\\Users\\sundb\\WebstormProjects\\BacBackend\\chromedriver.exe";
    private static final String driverName = "webdriver.chrome.driver";

    public WebDriver driver() {

        System.setProperty(driverName, path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.7049.85 Safari/537.36");

        return new ChromeDriver(options);
    }

}
