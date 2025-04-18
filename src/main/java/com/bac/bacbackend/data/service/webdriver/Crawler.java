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

    public String values(String s, String t) {
        getDriver();
        setElement(t);
        List<WebElement> e = getElements(str);
        return e.get(index).getAttribute(s);
    }

    public String value(String s) {
        getDriver();
        WebElement e = getElement(s);
        return e.getText();
    }

    public String redo(String s) {
        getDriver();
        WebElement e = getElement(s);
        return e.getAttribute("src");
    }

    private void setElement(String s) {
        str = s;
    }

    private void getDriver() {
        driver = br.getDriver();
    }

    private WebElement getElement(String s) {
        return driver.findElement(By.cssSelector(s));
    }

    private List<WebElement> getElements(String s) {
        return driver.findElements(By.cssSelector(s));
    }


    // *************** Code for further abstraction? ***************

    private String isTxt(WebElement wb) {
        try {
            String txt = wb.getText();
            if (!txt.trim().isEmpty()) {
                return txt;
            }

            String content = wb.getAttribute("content");
            if (content != null && !content.trim().isEmpty())
                return content;

            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
