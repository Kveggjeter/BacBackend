package com.bac.bacbackend.domain;

import jakarta.persistence.*;

@Entity
@Table(name="articleUrl")
public class ArticleUrl {

    public ArticleUrl() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;


}
