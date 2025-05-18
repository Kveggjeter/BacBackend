package com.bac.bacbackend.data.model.scraper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Postgres entity for the news sources
 */
@Data
@Entity
@Table(name = "scraper_objects")
public class ScraperObjectEntity {

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

    public ScraperObjectEntity() {}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    public String getTxtLocator() {return txtLocator;}
    public String getTxtHref() {return txtHref;}
    public String getImgLocator() {return imgLocator;}
    public String getImgHref() {return imgHref;}
    public String getTitle() {return title;}
    public String getSum() {return sum;}

}
