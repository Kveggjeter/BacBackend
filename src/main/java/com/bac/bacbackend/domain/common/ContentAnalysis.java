package com.bac.bacbackend.domain.common;

import com.bac.bacbackend.domain.model.article.ScrapeContext;
import java.util.ArrayList;

/**
 * Interface for contentanalysis
 */
public interface ContentAnalysis {
    ArrayList<String> analyzeContent(ScrapeContext scrapeContext);
}
