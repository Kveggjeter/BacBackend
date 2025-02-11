package com.bac.bacbackend.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@RedisHash("Article")
public class Article {

    @Id
    private String id;;
    private String sourceName;
    private String title;
    private String summary;
    private String city;
    private String category;
    private String imgUrl;
    private String ldt = LocalDateTime.now().toString();

    public Article() {}

    public Article(String id, String sourceName, String title, String summary, String city, String category, String imgUrl, String ldt) {
        this.id = id;
        this.sourceName = sourceName;
        this.title = title;
        this.summary = summary;
        this.city = city;
        this.category = category;
        this.imgUrl = imgUrl;
        this.ldt = ldt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public String getLdt() { return ldt; }
    public void setLdt(String ldt) { this.ldt = ldt; }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", city='" + city + '\'' +
                ", category='" + category + '\'' +
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
