package com.bac.bacbackend.domain.common.validators;

import com.bac.bacbackend.data.service.webdriver.WebSelectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Class for handling summary. Primarily to make sure it's not a null
 */
@RequiredArgsConstructor
@Component
public class SummaryValidator {

    private final WebSelectors webSelectors;

    /**
     * Does some nullchecks, a retry in case something unexpected happens. If the summary is longer than 400 characters,
     * we shorten it down to max 400. To change this, simply change the max amount at line 28
     *
     * @param summary string containing the summary of an article
     * @return the same summary as inserted (hopefully)
     */
    public String summaryHandler(String summary) {
        if (summary == null)
            summary = webSelectors.txtValue("p");
        if (summary == null)
            System.err.println("No summary found");
        if (summary != null && summary.length() > 400) summary = summary.substring(0, 400) + "...";
        return summary;
    }
}
