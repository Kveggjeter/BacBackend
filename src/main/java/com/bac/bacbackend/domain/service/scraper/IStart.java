package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.scraper.ArticleData;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Component
public interface IStart {
    ArrayList<ArticleData> crawl(int n);
}
