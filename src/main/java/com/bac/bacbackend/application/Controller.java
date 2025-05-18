package com.bac.bacbackend.application;

import com.bac.bacbackend.application.routine.crawling.NewsArticleCrawler;
import com.bac.bacbackend.application.routine.scraping.NewsArticleScraper;
import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.model.article.ArticleFacts;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.port.IArticleRepo;
import com.bac.bacbackend.domain.port.INewsParamRepo;
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
@CrossOrigin(origins = "*")
@RestController
public class Controller {

    private final IArticleRepo repo;
    private final NewsArticleCrawler newsArticleCrawler;
    private final INewsParamRepo nRepo;
    private final NewsArticleScraper newsArticleScraper;

    /**
     * Private constructor for dependency injection, instantiate the repository object.
     * @param repo repository interface used for queries
     */
    private Controller(IArticleRepo repo, NewsArticleCrawler newsArticleCrawler, NewsArticleScraper newsArticleScraper , INewsParamRepo nRepo) {
        this.repo = repo;
        this.newsArticleCrawler = newsArticleCrawler;
        this.nRepo = nRepo;
        this.newsArticleScraper = newsArticleScraper;
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

    /**
     * TESTING CONTROLLER FOR CRAWL, NOT FOR PRODUCTION
     */
    @RequestMapping("/crawl")
    public List<ArticleUrls> crawl(){
        return newsArticleCrawler.crawlWebsites(5, nRepo.sumOfAllSources());
    }

    /**
     * TESTING CONTROLLER FOR SCRAPING, NOT FOR PRODUCTION
     */
    @RequestMapping("/scrape")
    public String scrape(){
        ArticleUrls articleUrls = new ArticleUrls(
"https://thediplomat.com/2025/05/tajikistan-decriminalizes-likes-online/",
                "image.<div class=\"styles__image-container__3hkY5 styles__cover__34fjZ styles__center_center__1CNY5 styles__apply-ratio__1JYnB\" style=\"--aspect-ratio: 1.5;\"><img src=\"https://www.reuters.com/resizer/v2/I6ME2NDSBFJRLBYAUR637DYZHQ.jpg?auth=0d24ba8bbb067a78d6556ac40d59877156c1e4ef1a2bae6fa7ae4ed27bd40340&amp;width=1200&amp;quality=80\" srcset=\"https://www.reuters.com/resizer/v2/I6ME2NDSBFJRLBYAUR637DYZHQ.jpg?auth=0d24ba8bbb067a78d6556ac40d59877156c1e4ef1a2bae6fa7ae4ed27bd40340&amp;width=240&amp;quality=80 240w,https://www.reuters.com/resizer/v2/I6ME2NDSBFJRLBYAUR637DYZHQ.jpg?auth=0d24ba8bbb067a78d6556ac40d59877156c1e4ef1a2bae6fa7ae4ed27bd40340&amp;width=480&amp;quality=80 480w,https://www.reuters.com/resizer/v2/I6ME2NDSBFJRLBYAUR637DYZHQ.jpg?auth=0d24ba8bbb067a78d6556ac40d59877156c1e4ef1a2bae6fa7ae4ed27bd40340&amp;width=640&amp;quality=80 640w,https://www.reuters.com/resizer/v2/I6ME2NDSBFJRLBYAUR637DYZHQ.jpg?auth=0d24ba8bbb067a78d6556ac40d59877156c1e4ef1a2bae6fa7ae4ed27bd40340&amp;width=720&amp;quality=80 720w,https://www.reuters.com/resizer/v2/I6ME2NDSBFJRLBYAUR637DYZHQ.jpg?auth=0d24ba8bbb067a78d6556ac40d59877156c1e4ef1a2bae6fa7ae4ed27bd40340&amp;width=960&amp;quality=80 960w,https://www.reuters.com/resizer/v2/I6ME2NDSBFJRLBYAUR637DYZHQ.jpg?auth=0d24ba8bbb067a78d6556ac40d59877156c1e4ef1a2bae6fa7ae4ed27bd40340&amp;width=1080&amp;quality=80 1080w,https://www.reuters.com/resizer/v2/I6ME2NDSBFJRLBYAUR637DYZHQ.jpg?auth=0d24ba8bbb067a78d6556ac40d59877156c1e4ef1a2bae6fa7ae4ed27bd40340&amp;width=1200&amp;quality=80 1200w\" sizes=\"(min-width: 1024px) 680px, 100vw\" width=\"680\" height=\"537\" alt=\"Syrian President al-Sharaa meets with U.S. President Trump and Saudi Crown Prince Mohammed Bin Salman in Riyadh\"></div>."
        );
        newsArticleScraper.scrapeWebsite(articleUrls, 1);
        return "skrapa";
    }
}
