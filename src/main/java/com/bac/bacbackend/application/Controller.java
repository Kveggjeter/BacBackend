package com.bac.bacbackend.application;

import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.model.article.ArticleFacts;
import com.bac.bacbackend.domain.port.IArticleRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Rest controller that gives access to the web application to read news data.
 * Crossorigin is used to ensure that only the given port/address can receive the data.
 * Constructur is private to not be used anywhere else, as this is purely an endpoint controller.
 */
@CrossOrigin(origins = {
        "https://www.simplytidings.com/",
        "https://www.api.simplytidings.com/",
        "*"
})
@RestController
public class Controller {

    private final IArticleRepo repo;

    /**
     * Private constructor for dependency injection, instantiate the repository object.
     * @param repo repository interface used for queries
     */
    private Controller(IArticleRepo repo) {
        this.repo = repo;
    }

    /**
     * This method is for querying information about a given country. Iterates through
     * the articles saved and makes fetches out the relevant information that matches with "country".
     * It's a little forced to be O(n) since Redis don't have the same indexing possibilities as other databases.
     * It does not matter performance wise, as the data is seldom big.
     *
     * @param country the given country the receiver want info about
     * @return a JSON containing information about the country in question
     */
    @RequestMapping("/country")
    private ArticleFacts country(@RequestParam String country) {
        Iterable<Article> articles = repo.news();
        return ArticleFacts.getArticleFacts(country, articles);
    }

    /**
     * Fetching all the news to be placed on the web-application.
     *
     * @return a JSON containing all the news that are saved in the db
     */
    @RequestMapping("/news")
    private List<Article> getNews() {
        return repo.news();
    }
}
