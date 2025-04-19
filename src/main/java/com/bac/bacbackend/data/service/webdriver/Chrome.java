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
    private final ThreadLocal<WebDriver> td = new ThreadLocal<>();

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
        td.set(dr);
        dr.get(s);
    }

    @Override
    public WebDriver getDriver() {
        WebDriver dr = td.get();
        if (dr == null) {
            throw new IllegalStateException("Driver not created, call start again");
        }
        return dr;
    }

    @Override
    public void stop() {
        WebDriver dr = td.get();
        if (dr != null) {
            try {
                dr.quit();
            } catch (Exception e) {
                System.err.println("Error quitting driver: " + e);
            } finally {
                td.remove();
            }
        }
    }

}
