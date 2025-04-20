package com.bac.bacbackend.data.service.webdriver;

import org.openqa.selenium.WebDriver;

public abstract class BrowserInstance {

    private final ThreadLocal<WebDriver> td = new ThreadLocal<>();

    protected BrowserInstance() {}

    protected abstract WebDriver create();

    protected void setStart(String s) {
        WebDriver dr = create();
        td.set(dr);
        dr.get(s);
    }

    protected WebDriver setDriver() {
        WebDriver dr = td.get();
        if (dr == null) {
            throw new IllegalStateException("Driver not started");
        }
        return dr;
    }

    protected void setStop() {
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
