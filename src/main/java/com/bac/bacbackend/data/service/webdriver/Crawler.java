package com.bac.bacbackend.data.service.webdriver;

import com.bac.bacbackend.data.repository.browser.Browser;
import com.bac.bacbackend.domain.port.ICrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class Crawler implements ICrawler {

    private String str;
    private int index;
    private WebDriver driver;
    private final Browser br;

    private Crawler(Browser br) {
        this.br = br;
    }

    public void content(int n) {
        index = n;
    }

    public String value(String s, String t) {
        getDriver();
        setElement(t);
        List<WebElement> e = getElement(str);
        return e.get(index).getAttribute(s);
    }

    private void setElement(String s) {
        str = s;
    }

    private void getDriver() {
        driver = br.getDriver();
    }

    private List<WebElement> getElement(String s) {
        return driver.findElements(By.cssSelector(s));
    }

}
