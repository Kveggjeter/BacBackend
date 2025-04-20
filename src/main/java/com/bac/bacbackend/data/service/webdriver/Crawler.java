package com.bac.bacbackend.data.service.webdriver;

import com.bac.bacbackend.data.repository.browser.Browser;
import com.bac.bacbackend.domain.port.ICrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class Crawler implements ICrawler {

    private WebDriver driver;
    private final Browser br;

    private Crawler(Browser br) {
        this.br = br;
    }

    public List<String> values(String s) {
        return vars(s, e-> e.getAttribute("href"));
    }

    public List<String> values(String s, String t) {
        return vars(s, e -> e.getAttribute(t));
    }

    public String txtValue(String s) {
        return getElement(s).getText();
    }

    public String attValue(String s) {
        return getElement(s).getAttribute("href");
    }

    public String redo(String s) {
        return getElement(s).getAttribute("src");
    }

    private void getDriver() {
        driver = br.getDriver();
    }

    private List<String> vars(String s, Function<WebElement, String> f) {
        List<String> list = new ArrayList<>();
        for (WebElement e : getElements(s)) {
            list.add(f.apply(e));
        }
        return list;
    }

    private WebElement getElement(String s) {
        getDriver();
        return driver.findElement(By.cssSelector(s));
    }

    private List<WebElement> getElements(String s) {
        getDriver();
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
