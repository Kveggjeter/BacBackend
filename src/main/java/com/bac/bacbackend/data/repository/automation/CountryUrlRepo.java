package com.bac.bacbackend.data.repository.automation;

import com.bac.bacbackend.data.model.article.CountryUrlEntity;
import com.bac.bacbackend.domain.port.ICountryUrl;
import org.springframework.stereotype.Component;

@Component
public class CountryUrlRepo implements ICountryUrl {

    private final CountryDataUrl repo;

    public CountryUrlRepo(CountryDataUrl repo) {
        this.repo = repo;
    }

    public void save(String country, String url) {
        repo.save(add(country, url));
    }

    public boolean exist(String url) {
        return repo.existsById(url);
    }

    private CountryUrlEntity add(String country, String url) {
        CountryUrlEntity cu = new CountryUrlEntity();
        cu.setCountry(country);
        cu.setUrl(url);
        return cu;
    }

}
