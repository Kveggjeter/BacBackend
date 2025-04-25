package com.bac.bacbackend.domain.common;

import com.bac.bacbackend.domain.common.exceptions.RegexMatchResultException;
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

    protected String urlName(String urlInput) {
        Matcher patternMatcher = Pattern.compile(firstWord).matcher(urlInput);
        if (patternMatcher.find()) {
            String name = patternMatcher.group(1);
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        } else throw new RegexMatchResultException("No name was found for the url: " + urlInput);
    }

    protected String imgSrc(String imgInput) {
            Matcher patternMatcher = Pattern.compile("https?://[^\s\"']+").matcher(imgInput);
            String lastMatchUrlInSequence = null;
            while (patternMatcher.find()) lastMatchUrlInSequence = patternMatcher.group();
            if (lastMatchUrlInSequence == null) return lastMatchUrlInSequence;
            return lastMatchUrlInSequence.replace(";", "").replace("amp", "");
    }

}



