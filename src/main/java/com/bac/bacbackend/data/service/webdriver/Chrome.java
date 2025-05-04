package com.bac.bacbackend.data.service.webdriver;

import com.bac.bacbackend.data.model.browser.ChromeBrowser;
import com.bac.bacbackend.data.repository.browser.Browser;
import com.bac.bacbackend.domain.port.IChrome;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Final child class of BrowserInstance. Implements Browser to be used in the data layer, and IChrome to be used
 * in the domain and forward.
 */
@RequiredArgsConstructor
@Component
public final class Chrome extends BrowserInstance implements Browser, IChrome {

    private final ChromeBrowser props;

    /**
     * Creates an instance of Google Chrome as headless. Uses given properties to camouflage itself {@code props}.
     * These properties have to be changed in the application .yaml file. If there is a lot of thread time-outs, it
     * may be caused by the properties/alias being outdated as the Chrome browser and chromedriver is constantly updated,
     * but the user agent is not.
     *
     * @return the new chrome instance
     */
    @Override
    public WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        if (props.headless()) options.addArguments("--headless=old");
        String uniqueUserDataDir = "/tmp/chrome-user-data-" + UUID.randomUUID();
        options.addArguments("--user-data-dir=" + uniqueUserDataDir);
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("user-agent=" + props.alias());

        try {
            return new RemoteWebDriver(new URL(props.driverPath()), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Bad Selenium URL: " + props.driverPath(), e);
        }
    }

    /**
     * Echoing the method from parent class.
     * @return the current browser instance
     */
    @Override
    public WebDriver getDriver() {
        return setDriver();
    }

    /**
     * Starting the browser instance and giving it directions.
     *
     * @param s represents the url used for where the browser is to start its crawling/scraping
     */
    @Override
    public void start(String s) {
        setStart(s);
    }

    /**
     * Stopping the browser instance
     */
    @Override
    public void stop() {
        setStop();
    }

}
