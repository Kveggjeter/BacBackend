package com.bac.bacbackend.data.datasource.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;

@RedisHash("Article")
public class Article {

    @Id
    private String id;
    private String sourceName;
    private String url;
    private String title;
    private String imgUrl;

    public Article() {}

    public Article(String id, String sourceName, String url, String title, String imgUrl) {
        this.id = id;
        this.sourceName = sourceName;
        this.url = url;
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
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
