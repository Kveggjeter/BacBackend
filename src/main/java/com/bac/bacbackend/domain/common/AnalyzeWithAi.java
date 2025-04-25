package com.bac.bacbackend.domain.common;

import com.bac.bacbackend.domain.common.exceptions.AiPromptException;
import com.bac.bacbackend.domain.common.exceptions.RegexMatchResultException;
import com.bac.bacbackend.domain.common.validators.Boundaries;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.IOpenAi;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Component for analyzing scraped content using LLM and regex.
 */
@Component
public class AnalyzeWithAi implements ContentAnalysis {

    private final IOpenAi openAi;
    private final ExtractInfoWithRegex extractInfoWithRegex;
    private static final String COMMAND = StringResource.COMMAND.getValue();

    public AnalyzeWithAi(IOpenAi openAi, ExtractInfoWithRegex extractInfoWithRegex) {
        this.openAi = openAi;
        this.extractInfoWithRegex = extractInfoWithRegex;
    }

    /**
     * Analyzes content of the scraped article using LLM and some simple Regex.
     * {@link IOpenAi} is an interface, so to use another model simply make a new implementation.
     * {@link ExtractInfoWithRegex} is a child-class of {@link Regex}, so for further
     * optimization make a new child-class.
     *
     * @param scrapeContext data class that defines the scrapers properties
     * @return a List of analyzed content
     * @throws AiPromptException if LLM fails to return after several retries
     * @throws RegexMatchResultException if regex extraction fails
     */
    @Override
    public ArrayList<String> analyzeContent(ScrapeContext scrapeContext) throws AiPromptException, RegexMatchResultException {
            String[] promptResult = analyzeContentWithAi(scrapeContext);
            ArrayList<String> contents = new ArrayList<>(Arrays.asList(promptResult));
            contents.add(extractInfoWithRegex.getNameOfUrlSource(scrapeContext));
            contents.add(extractInfoWithRegex.getCorrectImg(scrapeContext));
            return contents;
    }

    /**
     * This method analyzes the given content with an LLM and gives us back variables that we use
     * for geo-referencing our scraped news-article.
     * Makes use of {@link Boundaries} for determining if the coordinates makes any sense.
     * We attempt a prompt 3 times if we're not given correct information.
     * <p>
     * For modifying this, {@code COMMAND} is the instruction about what type of answer we accept
     * from the LLM. That will remain constant throughout all prompts. The message for each individual
     * prompts can be modified with {@code promptMessage}. The retries should be kept at a minimum
     * of 3 as the LLM tends to do LLM-stuff, but to increase/decrease simply change the
     * max iterations in the for-loop.
     *
     * @exception AiPromptException throws after 3 failed attempts
     * @param scrapeContext data class that defines the scrapers properties
     * @return an array containing variables given by the LLM based on our inputs
     */
    private String[] analyzeContentWithAi(ScrapeContext scrapeContext) {
        String promptMessage = scrapeContext.getTitle() + " " + scrapeContext.getSummary();
        Boundaries boundaries = new Boundaries();

        for (int attempt = 1; attempt < 3; attempt++) {
            String[] promptResult = openAi.prompt(COMMAND, promptMessage).split("/");
            if (promptResult.length == 6 && boundaries.coordinatesChecker(promptResult))
                return promptResult;
            System.err.println("[" + Thread.currentThread().getName() + "] "
                    + "[" + attempt + "/3] Invalid result from prompt");
        }
        throw new AiPromptException("[" + Thread.currentThread().getName() + "] "
                + "Invalid response from prompt after 3 attempts");
    }
}
