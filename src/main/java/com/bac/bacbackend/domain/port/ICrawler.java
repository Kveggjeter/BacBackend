package com.bac.bacbackend.domain.port;

import java.util.List;

public interface ICrawler {
    List<String> values(String s, String t);
    List<String> values(String s);
    String attValue(String s);
    String txtValue(String s);
    String redo(String s);
}
