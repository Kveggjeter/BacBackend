package com.bac.bacbackend.domain.port;

public interface ICrawler {
    String values(String s, String t);
    String value(String s);
    String redo(String s);
    void content(int n);
}
