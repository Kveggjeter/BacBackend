package com.bac.bacbackend.domain.common;

import com.bac.bacbackend.domain.common.exceptions.RegexMatchResultException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contains different regexes for handling html results
 * Is used as a base class, but with the ability to create it as its own instance. In its current form, it is
 * used with inheritance, but might as well stay as a component in this package.
 * {@link package com.bac.bacbackend.domain.common;}. It is to be abstracted at a later point, as the idea is to
 * be less dependent on LLM to determine category and georeferences.
 */
@Component
public class Regex {
    @Value("${regex.firstWord}")
    private String firstWord;

    /**
     * Empty constructor, can be used to borrow methods
     */
    protected Regex() {}

    /**
     * Uses {@link java.util.regex.Matcher} and {@link java.util.regex.Pattern} to get the first word found
     * after the http-indentations. Returns the word with capital letter on the first character.
     *
     * @param urlInput the url where we want to get a name from
     * @return name of the new source behind the url
     */
    protected String urlName(String urlInput) {
        Matcher patternMatcher = Pattern.compile(firstWord).matcher(urlInput);
        if (patternMatcher.find()) {
            String name = patternMatcher.group(1);
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        } else throw new RegexMatchResultException("No name was found for the url: " + urlInput);
    }

    /**
     * Uses {@link java.util.regex.Matcher} and {@link java.util.regex.Pattern} to target the last url found based on
     * the regex. HTML from the image element often contain several images and are sorted chronologically to size. The
     * biggest always appears as the last clickable link. This method also replaces semicolons and the string "amp",
     * which seem to appear randomly in the urls.
     *
     * @param imgInput HTML element as a string, representing where the image might be
     * @return the last url found in the string
     */
    protected String imgSrc(String imgInput) {
            Matcher patternMatcher = Pattern.compile("https?://[^\s\"']+").matcher(imgInput);
            String lastMatchUrlInSequence = null;
            while (patternMatcher.find()) lastMatchUrlInSequence = patternMatcher.group();
            if (lastMatchUrlInSequence == null) return lastMatchUrlInSequence;
            return lastMatchUrlInSequence.replace(";", "").replace("amp", "");
    }

}



