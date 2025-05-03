package com.bac.bacbackend.data.service.webdriver;

import org.openqa.selenium.WebDriver;

/**
 * Abstract base class for all browser instances. Responsible for making thread-safe browser instances available
 * to the children classes. Uses Selenium. The importance of the instances being thread-safe is because when
 * a thread times out its important that it also kills the browser instance with it. It's luckily less of an issue now,
 * as the killing process is quite robust at the moment. There is failsafe outside application, by standard that docker
 * host the sessions and also kills of when they run idle to long.
 */
public abstract class BrowserInstance {

    private final ThreadLocal<WebDriver> td = new ThreadLocal<>();

    /**
     * No usage for the constructor
     */
    protected BrowserInstance() {}

    /**
     * Abstract and mandatory method that needs implementation for creating an instance.
     * @return the new webdriver instance
     */
    protected abstract WebDriver create();

    /**
     * Creating a thread-safe browser instance with the global variable of {@code td}, the local thread. That way
     * the thread with the instance can be killed of when something unexpected occurs
     *
     * @param s represents the url where we mark the start of our instance
     */
    protected void setStart(String s) {
        WebDriver dr = create();
        td.set(dr);
        dr.get(s);
    }

    /**
     * Gets the current driver instance, used to keep track of what driver belongs to what process.
     *
     * @return the current driver instance
     */
    protected WebDriver setDriver() {
        return td.get();
    }

    /**
     * Method for shutting the instance down and removing the thread. It catches exceptions from both selenium
     * driver related problems and thread-related. The catch message is usually not that important, as a failed shutdown
     * usually is because of a timeout. There is a double shutdown here just to be safe.
     */
    protected void setStop() {
        WebDriver dr = td.get();
        if (dr != null) {
            try {
                dr.quit();
            } catch (Exception e) {
                System.err.println("Error quitting driver: " + e);
            } finally {
                dr.quit();
                td.remove();
            }
        }
    }
}
