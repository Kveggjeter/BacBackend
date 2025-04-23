package com.bac.bacbackend.domain.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contains different regexes for handling html results
 */
@Component
public class Regex {
    @Value("${regex.firstWord}")
    private String firstWord;

    protected Regex() {}

    /**
     * Input an url where you want the name of the owner.
     *
     * @param urlInput the variable containing the url
     * @return the name of the news-source
     */
    protected String urlName(String urlInput) {
        Matcher patternMatcher = Pattern.compile(firstWord).matcher(urlInput);
        if (patternMatcher.find()) {
            String name = patternMatcher.group(1);
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        } else return null;
    }

    /**
     * Input an image url you want a href link to. The biggest image is usually the last
     * link in the html - element
     *
     * @param imgInput the variable containing the image url
     * @return the last image link present
     */
    protected String imgSrc(String imgInput) {
        Matcher patternMatcher = Pattern.compile("https?://[^\s\"']+").matcher(imgInput);
        String lastMatchUrlInSequence = null;
        while (patternMatcher.find()) lastMatchUrlInSequence = patternMatcher.group();
        if (lastMatchUrlInSequence == null) return null;
        return lastMatchUrlInSequence.replace(";", "").replace("amp", "");
    }

}



