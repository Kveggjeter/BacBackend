package com.bac.bacbackend.domain.common.validators;

import com.bac.bacbackend.data.service.webdriver.WebSelectors;
import org.springframework.stereotype.Component;

@Component
public class SummaryValidator {

    private final WebSelectors webSelectors;

    public SummaryValidator(WebSelectors webSelectors) {
        this.webSelectors = webSelectors;
    }

    public String summaryHandler(String summary) {
        if (summary == null)
            summary = webSelectors.txtValue("p");
        if (summary == null)
            System.err.println("No summary found");
        if (summary != null && summary.length() > 400) summary = summary.substring(0, 400) + "...";
        return summary;
    }
}
