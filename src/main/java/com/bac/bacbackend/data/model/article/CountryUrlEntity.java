package com.bac.bacbackend.data.model.article;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="country_urls")
public class CountryUrlEntity {

    private String country;
    @Id
    private String url;
}
