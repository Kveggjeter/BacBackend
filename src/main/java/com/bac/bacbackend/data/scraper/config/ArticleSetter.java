package com.bac.bacbackend.data.scraper.config;

import com.bac.bacbackend.data.model.Article;
import com.bac.bacbackend.data.repository.ArticleRepository;
import org.springframework.stereotype.Component;

@Component
public class ArticleSetter extends DataSourceHandler<Article, String> {

    public ArticleSetter(ArticleRepository repo) { super(repo); }

    public void adders(
            String id,
            String title,
            String summary,
            String city,
            String country,
            String continent,
            String category,
            String x,
            String y,
            String imgUrl,
            String sourceName
    ) {
        Article a = new Article();
                a.setId(id);
                a.setSourceName(sourceName);
                a.setTitle(title);
                a.setSummary(summary);
                a.setCity(city);
                a.setCountry(country);
                a.setContinent(continent);
                a.setCategory(category);
                a.setX(x);
                a.setY(y);
                a.setImgUrl(imgUrl);
                try {
                    add(a);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    System.out.println("Failed adding article");
                }
                System.out.println("Added article");
    }
}
