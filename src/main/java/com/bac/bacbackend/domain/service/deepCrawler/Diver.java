package com.bac.bacbackend.domain.service.deepCrawler;

import com.bac.bacbackend.data.repository.browser.Browser;
import com.bac.bacbackend.domain.model.scraper.CountryData;
import com.bac.bacbackend.domain.port.ICountryUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Diver {

    private WebDriver driver;
    private final Browser br;
    private final String url = "https://www.worldpress.org/gateway.htm";
    private final ICountryUrl cu;
    private HashMap<String, String> data;

    public Diver(Browser br, ICountryUrl cu) {
        this.br = br;
        this.cu = cu;
    }

    public void dive() {
        br.start(url);
        driver = br.getDriver();
        try {
            List<WebElement> countryElement = driver.findElements(By.cssSelector("#content a"));
            List<WebElement> countryUrlElement = driver.findElements(By.cssSelector("#content a"));

            System.out.println("HER KOMMER TILHØRENDE LAND:");

            for (int i = 0; i < countryElement.size(); i++) {
                addData(countryElement.get(i).getText(), countryUrlElement.get(i).getAttribute("href"));
            }
        }finally {
            br.stop();
        }
    }


    private void deepDive() {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String country = entry.getKey();
            String countryUrl = entry.getValue();


        }
    }

    private void useList() {
        if (data == null)
            data = new HashMap<>();
    }

    private void addData(String country, String countryUrl) {
        useList();
        if(
                countryUrl != null
                && !cu.exist(countryUrl)
                && !data.containsKey(country)
        )
            data.put(country, countryUrl);
    }

}