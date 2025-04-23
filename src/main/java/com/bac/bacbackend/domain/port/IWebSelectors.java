package com.bac.bacbackend.domain.port;

import java.util.List;

public interface IWebSelectors {
    List<String> values(String s, String t);
    List<String> values(String s);
    String attributeValue(String s);
    String txtValue(String s);
    String redo(String s);
}
