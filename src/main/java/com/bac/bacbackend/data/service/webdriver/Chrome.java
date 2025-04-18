package com.bac.bacbackend.data.service.webdriver;

import com.bac.bacbackend.data.model.browser.ChromeBrowser;
import com.bac.bacbackend.data.repository.browser.Browser;
import com.bac.bacbackend.domain.port.IChrome;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public final class Chrome implements Browser, IChrome {

    private final ChromeBrowser props;
    private WebDriver driver;

    public Chrome(ChromeBrowser props) {
        this.props = props;
    }

    @Override
    public WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        if (props.headless()) options.addArguments("--headless=new");
        options.addArguments("user-agent=" + props.alias());
        return new ChromeDriver(options);
    }

    @Override
    public void start(String s) {
        WebDriver dr = create();
        driver = dr;
        dr.get(s);
    }

    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void stop() {
        driver.quit();
    }

}
