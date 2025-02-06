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
    @Value("${regex.link}")
    private String link;

    public String urlName(String s) {
        Matcher mx = Pattern.compile(firstWord).matcher(s);
        if (mx.find()) {
            String name = mx.group(1);
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        } else return null;
    }

    public String cityName(String s) {
        int comma = s.indexOf(',');
        String fp = comma != -1 ? s.substring(0, comma) : s;
        String lp = fp.replaceAll("\\s", "");
        String city = lp.replaceAll(alph, "");
        return city.isEmpty() ? "" : city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
    }

    public String imageSrc(String s) {
        Matcher mx = Pattern.compile("https?://[^\s\"']+").matcher(s);
        String lm = null;
        while (mx.find()) lm = mx.group();
        return lm.replace(";", "").replace("amp", "");
    }

}

//    public static void main (String[] args) {
//        String text = "<div class=\\\"styles__image-container__3hkY5 styles__cover__34fjZ styles__center_center__1CNY5 styles__apply-ratio__1JYnB\\\" style=\\\"--aspect-ratio: 1.5;\\\"><img src=\\\"https://www.reuters.com/resizer/v2/ZDYS7WURMFJTPP47TTPBBR3BMY.jpg?auth=075d75b3b3a38428de95d9a8123a4ba6fef53db72f3baa03f1fd9b440154de5c&amp;width=1200&amp;quality=80\\\" srcset=\\\"https://www.reuters.com/resizer/v2/ZDYS7WURMFJTPP47TTPBBR3BMY.jpg?auth=075d75b3b3a38428de95d9a8123a4ba6fef53db72f3baa03f1fd9b440154de5c&amp;width=240&amp;quality=80 240w,https://www.reuters.com/resizer/v2/ZDYS7WURMFJTPP47TTPBBR3BMY.jpg?auth=075d75b3b3a38428de95d9a8123a4ba6fef53db72f3baa03f1fd9b440154de5c&amp;width=480&amp;quality=80 480w,https://www.reuters.com/resizer/v2/ZDYS7WURMFJTPP47TTPBBR3BMY.jpg?auth=075d75b3b3a38428de95d9a8123a4ba6fef53db72f3baa03f1fd9b440154de5c&amp;width=640&amp;quality=80 640w,https://www.reuters.com/resizer/v2/ZDYS7WURMFJTPP47TTPBBR3BMY.jpg?auth=075d75b3b3a38428de95d9a8123a4ba6fef53db72f3baa03f1fd9b440154de5c&amp;width=1200&amp;quality=80 1200w\\\" sizes=\\\"(min-width: 1024px) 680px, 100vw\\\" width=\\\"5477\\\" height=\\\"3651\\\" alt=\\\"A Palestinian sits among the rubble of buildings destroyed during the Israeli offensive in Rafah\\\"></div>";
//        String lenke = imageSrc(text);
//        System.out.println(lenke);
//    }


