package com.bac.bacbackend.data.datasource.scraperUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Regex {
    @Value("${regex.alph}")
    private String alph;
    @Value("${regex.firstWord}")
    private String firstWord;

    public String urlName(String s) {
        Matcher mx = Pattern.compile(firstWord).matcher(s);
        if (mx.find()) {
            String name = mx.group(1);
            return name.substring(0,1).toUpperCase() + name.substring(1);
        }
        else return null;
    }

    public String cityName(String s) {
        int comma = s.indexOf(',');
        String fp = comma != -1 ? s.substring(0, comma) : s;
        String lp = fp.replaceAll("\\s", "");
        String city = lp.replaceAll(alph, "");

        return city.isEmpty() ? "" : city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
    }
}
