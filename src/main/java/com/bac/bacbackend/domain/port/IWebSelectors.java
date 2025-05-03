package com.bac.bacbackend.domain.port;

import java.util.List;

/**
 * Interface for accessing elements used for scraping and crawling. {@code attributeValue(String s} are not currently
 * used in this iteration, as every value currently is "href". It can still be used for further implementations, so it
 * stays here nonetheless. It is still a mandatory value to add both in the data object class and in the database when
 * adding new properties.
 */
public interface IWebSelectors {
    List<String> values(String s, String t);
    List<String> values(String s);
    String attributeValue(String s);
    String txtValue(String s);
    String redo(String s);
}
