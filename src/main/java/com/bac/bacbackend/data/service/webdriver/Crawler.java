package com.bac.bacbackend.data.service.webdriver;

import com.bac.bacbackend.data.repository.browser.Browser;
import com.bac.bacbackend.domain.port.ICrawler;
import org.openqa.selenium.*;
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

    public String txtValue(String select) {
        return retry(select, s -> getElement(s).getText());
    }

    public String attValue(String select) {
        return retry(select, s -> getElement(s).getAttribute("href"));

    }

    public String redo(String s) {
        return getElement(s).getAttribute("src");
    }

    private void getDriver() {
        driver = br.getDriver();
    }

    private String retry(String s, Function<String, String> f) {
        int count = 3;
        do {
            try {
                return f.apply(s);
            } catch (StaleElementReferenceException e) {
                count--;
            }
        }  while (count != 3 && count >= 0) ;
        throw new NoSuchElementException(s);
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

//public String values(String attr, String selector) {
//    int retries = 3;
//    while (retries-- > 0) {
//        try {
//            List<WebElement> elements = driver.findElements(By.cssSelector(selector));
//            if (index >= elements.size()) return null;
//            return elements.get(index).getAttribute(attr);
//        } catch (StaleElementReferenceException e) {
//            // DOM har endret seg – prøv på nytt
//        }
//    }
//    throw new RuntimeException("Kunne ikke hente elementet etter flere forsøk");
//}

//public interface CheckSupp<T> {
//    T get() throws StaleElementReferenceException;
//}
//
//public static <T> T retry (CheckSupp<T> supp) throws StaleElementReferenceException {
//    StaleElementReferenceException last = null;
//
//    for (int i = 0; i < 3; i++) {
//        try {
//            return supp.get();
//        } catch (StaleElementReferenceException e) {
//            last = e;
//        }
//    }
//    throw last;
//}
