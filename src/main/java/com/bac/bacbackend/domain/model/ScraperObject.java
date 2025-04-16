package com.bac.bacbackend.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "scraper_objects")
public class ScraperObject {

    @Column(name = "website")
    private String website;
    @Id
    @Column(name = "url")
    private String url;
    @Column(name = "txtlocator")
    private String txtLocator;
    @Column(name = "txthref")
    private String txtHref;
    @Column(name = "imglocator")
    private String imgLocator;
    @Column(name = "imghref")
    private String imgHref;
    @Column(name = "title")
    private String title;
    @Column(name = "sum")
    private String sum;

    public ScraperObject() {}

    public ScraperObject(
            String website, String url, String txtLocator, String txtHref, String imgLocator,
            String imgHref, String title, String sum
    ) {
        this.website = website;
        this.url = url;
        this.txtLocator = txtLocator;
        this.txtHref = txtHref;
        this.imgLocator = imgLocator;
        this.imgHref = imgHref;
        this.title = title;
        this.sum = sum;
    }
}
