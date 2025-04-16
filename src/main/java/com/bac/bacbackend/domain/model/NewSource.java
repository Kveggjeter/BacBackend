package com.bac.bacbackend.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;

@Getter
@Setter
@RedisHash(value = "NewSource")
public class NewSource {

    @Id
    private String url;
    private String txtLocator;
    private String txtHref;
    private String imgLocator;
    private String imgHref;
    private String title;
    private String sum;

    public NewSource() {}

    public NewSource(
            String url, String txtLocator, String txtHref, String imgLocator,
            String imgHref, String title, String sum
    ) {
        this.url = url;
        this.txtLocator = txtLocator;
        this.txtHref = txtHref;
        this.imgLocator = imgLocator;
        this.imgHref = imgHref;
        this.title = title;
        this.sum = sum;
    }

    @Override
    public int hashCode() { return Objects.hash(url); }
}
