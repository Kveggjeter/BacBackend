package com.bac.bacbackend.domain.common;

import com.bac.bacbackend.domain.common.validators.Boundaries;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.IOpenAi;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class AnalyzeWithAi implements ContentAnalyzis  {

    private final IOpenAi openAi;
    private final ExtractInfoWithRegex extractInfoWithRegex;
    private final String COMMAND = StringResource.COMMAND.getValue();

    public AnalyzeWithAi(IOpenAi openAi, ExtractInfoWithRegex extractInfoWithRegex) {
        this.openAi = openAi;
        this.extractInfoWithRegex = extractInfoWithRegex;
    }

    @Override
    public ArrayList<String> analyzeContent(ScrapeContext scrapeContext) {
        try {
            String[] promptResult = analyzeContentWithAi(scrapeContext);
            ArrayList<String> contents = new ArrayList<>(Arrays.asList(promptResult));
            contents.add(extractInfoWithRegex.getNameOfUrlSource(scrapeContext));
            contents.add(extractInfoWithRegex.getCorrectImg(scrapeContext));
            return contents;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String[] analyzeContentWithAi(ScrapeContext scrapeContext) {
        String[] promptResult;
        int retryAttempts = 0;
        boolean isCoordinatesValid;
        Boundaries boundaries = new Boundaries();

        do {
            promptResult = openAi.prompt(COMMAND, scrapeContext.getTitle()
                    + " "
                    + scrapeContext.getSummary()).split("/");
            isCoordinatesValid = boundaries.coordinatesChecker(promptResult);
            retryAttempts++;
        }while(promptResult.length != 6 || retryAttempts > 3 || !isCoordinatesValid);

        return promptResult;
    }

}
