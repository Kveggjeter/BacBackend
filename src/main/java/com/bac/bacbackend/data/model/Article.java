package com.bac.bacbackend.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * Getter/setter source: https://www.baeldung.com/lombok-omit-getter-setter
 *
 * 24h = 86400
 * 48h = 172800
 * 72h = 259200
 */
@RedisHash(value = "Article", timeToLive = 172800)
public class Article {

    @Id
    private String id;;
    private String sourceName;
    private String title;
    private String summary;
    private String city;
    private String country;
    private String region;
    private String continent;
    private String category;
    private String x;
    private String y;
    private String imgUrl;
    private String ldt = LocalDateTime.now().toString();

    public Article() {}

    public Article(
            String id, String sourceName, String title, String summary, String city, String country,
            String region, String continent, String category, String x, String y, String imgUrl, String ldt
    ) {
        this.id = id;
        this.sourceName = sourceName;
        this.title = title;
        this.summary = summary;
        this.city = city;
        this.country = country;
        this.region = region;
        this.continent = continent;
        this.category = category;
        this.x = x;
        this.y = y;
        this.imgUrl = imgUrl;
        this.ldt = ldt;
    }

    public void setId(String id) { this.id = id; }
    public String getId() { return id; }

    public void setSourceName(String sourceName) { this.sourceName = sourceName; }
    public String getSourceName() { return sourceName; }

    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }

    public void setSummary(String summary) { this.summary = summary; }
    public String getSummary() { return summary; }

    public void setCity(String city) { this.city = city; }
    public String getCity() { return city; }

    public void setCountry(String country) { this.country = country; }
    public String getCountry() { return country; }

    public void setRegion(String region) { this.region = region; }
    public String getRegion() { return region; }

    public void setContinent(String continent) { this.continent = continent; }
    public String getContinent() { return continent; }

    public void setCategory(String category) { this.category = category; }
    public String getCategory() { return category; }

    public void setX(String x) { this.x = x; }
    public String getX() { return x; }

    public void setY(String y) { this.y = y; }
    public String getY() { return y; }

    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    public String getImgUrl() { return imgUrl; }

    public void setLdt(String ldt) { this.ldt = ldt; }
    public String getLdt() { return ldt; }



    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", continent='" + continent + '\'' +
                ", category='" + category + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
