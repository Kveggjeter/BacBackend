package com.bac.bacbackend.domain.service.scraping;

import com.bac.bacbackend.domain.common.ContentAnalysis;
import com.bac.bacbackend.domain.common.exceptions.AiPromptException;
import com.bac.bacbackend.domain.common.exceptions.RegexMatchResultException;
import com.bac.bacbackend.domain.common.validators.SummaryValidator;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.*;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.StaleElementReferenceException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * {@link ScrapingStrategy} implementation for scraping articles for a news-source
 * <p>
 * This strategy extract the title and summary, feeding them into the LLM content-analysis.
 * The result we get from that then determines the rest of our values, like coordinates,
 * country, category and so forth.
 */
public class ArticleScrapingStrategy implements ScrapingStrategy {

    private final IWebSelectors webSelectors;
    private final SummaryValidator summaryValidator;
    private final ContentAnalysis contentAnalysis;

    public ArticleScrapingStrategy(IWebSelectors webSelectors, SummaryValidator summaryValidator, ContentAnalysis contentAnalysis) {
        this.webSelectors = webSelectors;
        this.summaryValidator = summaryValidator;
        this.contentAnalysis = contentAnalysis;
    }

    /**
     * Executing the scraping logic for the given {@link ScrapeContext}
     * <p>
     * If we run into some unexpected issue, we will catch these and turn the
     * method into false. The scraped article will later be added to the "failed"
     * Redis-set, so we can skip it if we encounter it later on.
     * <p>
     * With no throws, the method is true and the values can be safely saved to the db.
     *
     * @param scrapeContext data class that defines the scraped properties
     * @return a boolean statement if scraping and analysis succeed.
     */
    @Override
    public boolean doScrape(ScrapeContext scrapeContext) {
        try {
            scrapeContext.setTitle(webSelectors.txtValue(scrapeContext.getScrapingProperties().title()));
            scrapeContext.setSummary(webSelectors.txtValue(scrapeContext.getScrapingProperties().sum()));
            scrapeContext.setSummary(summaryValidator.summaryHandler(scrapeContext.getSummary()));

            System.out.println("[" + Thread.currentThread().getName() + "] " + "title: " + scrapeContext.getTitle());
            System.out.println("[" + Thread.currentThread().getName() + "] " + "summary: " + scrapeContext.getSummary());

            ArrayList<String> analyzedContent = contentAnalysis.analyzeContent(scrapeContext);
            if (analyzedContent.size() == 8) {
                scrapeContext.setCity(analyzedContent.get(0));
                scrapeContext.setCountry(analyzedContent.get(1));
                scrapeContext.setContinent(analyzedContent.get(2));
                scrapeContext.setCategory(analyzedContent.get(3));
                scrapeContext.setX(analyzedContent.get(4));
                scrapeContext.setY(analyzedContent.get(5));
                scrapeContext.setSourceName(analyzedContent.get(6));
                scrapeContext.setImgUrl(analyzedContent.get(7));
                return true;
            }

        } catch (NoSuchElementException | StaleElementReferenceException e) {
            System.err.println("[" + Thread.currentThread().getName() + "] " + "Can't extract info from:" + scrapeContext.getArticleUrl()+ "\n" + e.getMessage());
        }
        catch (RegexMatchResultException | AiPromptException e) {
            System.err.println("[" + Thread.currentThread().getName() + "] " + scrapeContext.getArticleUrl() + "failed:" + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("[" + Thread.currentThread().getName() + "]" + " Unexpected error scraping article" + scrapeContext.getArticleUrl() + "\n" + e.getMessage());
        }
        return false;
    }

}
