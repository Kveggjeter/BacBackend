package com.bac.bacbackend.data.service.webdriver;

import com.bac.bacbackend.data.repository.browser.Browser;
import com.bac.bacbackend.domain.port.IWebSelectors;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Web selectors and other selenium functions. The class functions as a base class
 * but is not primarily intended for it. It houses selector properties and can be built
 * upon to make even more advanced selector-parameters. Most suited for dependency-injection,
 * as it's in the Data layer.
 */
@Component
public class WebSelectors implements IWebSelectors {

    private WebDriver driver;
    private final Browser browser;

    private WebSelectors(Browser browser) {
        this.browser = browser;
    }

    /**
     * Getting values and putting them into a list, later to be iterated. Primarily meant
     * for finding potential paths to crawl further. Uses a lambda expression to use
     * addValuesToList()
     *
     * @param select cssSelector
     * @return ist of values in a string
     */
    public List<String> values(String select) {
        return addValuesToList(select, s -> s.getAttribute("href"));
    }

    /**
     * Overload method for values. Uses a lambda expression to use addValuesToList() and get
     * the elements and putting them into a list, ready to be used.
     *
     * @param select cssSelector
     * @param attribute getAttribute-selector
     * @return list of values in a string
     */
    public List<String> values(String select, String attribute) {
        return addValuesToList(select, s -> s.getAttribute(attribute));
    }

    /**
     * For getting text values like title, summary and such
     *
     * @param select cssSelector
     * @return web element as String
     */
    public String txtValue(String select) {
        return retry(select, s -> getElement(s).getText());
    }

    /**
     * For getting links and other values that are not put into a text block
     *
     * @param select cssSelector
     * @return web element as String
     */
    public String attributeValue(String select) {
        return retry(select, s -> getElement(s).getAttribute("href"));

    }

    /**
     * A single redo, used as a hail-mary if no other check was successful. Sometimes
     * a src does the trick for pictures.
     *
     * @param select cssSelector
     * @return the element in string format
     */
    public String redo(String select) {
        return getElement(select).getAttribute("src");
    }

    /**
     * Gets the current active web-driver
     */
    private void getDriver() {
        driver = browser.getDriver();
    }

    /**
     * Sometimes the dom changes when loading. Therefor a retry has had some success with
     * getting past those javascript walls or other random instances. It retries only 3 times
     * not to waste too much time, but as crude as it looks it works most of the time you
     * will encounter either a StaleElementReferenceException or a NoSuchElementException.
     * Can be used in combination with all the other methods, simply put the other method
     * as a parameter with lambda (see txtValue or attributeValue for reference)
     *
     * @param select cssSelector
     * @param function a generic function
     * @return the element as a string
     */
    private String retry(String select, Function<String, String> function) {
        int count = 3;
        do {
            try {
                return function.apply(select);
            } catch (StaleElementReferenceException e) {
                count--;
            }
        } while (count != 3 && count >= 0);
        return null;
    }



    /**
     * Generic method for getting values to a list. Lambda expressions needs to be used
     * for inserting if you want to use getText() or getAttribute(). It takes the
     * cssSelector as an argument, together with a function. The function represents
     * the abstract Selenium methods for how to contextualize the elements.
     *
     * @param select the cssSelector
     * @param function a generic function that may differ from what type of attribute needed
     * @return the values in a list of strings
     */
    private List<String> addValuesToList(String select, Function<WebElement, String> function) {
        List<String> list = new ArrayList<>();
        for (WebElement element : getElements(select)) {
            list.add(function.apply(element));
        }
        return list;
    }

    /**
     * Getting a single web element
     *
     * @param select the cssSelector
     * @return a webelement with prompted attributes
     */
    private WebElement getElement(String select) {
        getDriver();
        return driver.findElement(By.cssSelector(select));
    }

    /**
     * Getting all web-elements that matches with the selector
     *
     * @param select the cssSelector
     * @return a list of web-elements with prompted attributes
     */
    private List<WebElement> getElements(String select) {
        getDriver();
        return driver.findElements(By.cssSelector(select));
    }
}



