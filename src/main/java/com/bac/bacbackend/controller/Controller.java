package com.bac.bacbackend.controller;

import com.bac.bacbackend.data.datasource.model.Article;
import com.bac.bacbackend.data.repository.ArticleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    @Autowired
    private ArticleRepository repo;

    @RequestMapping("/")
    public String index() { return "Application is running"; }

    @RequestMapping("/read")
    public String read() { return repo.findById("1").toString(); }

    @RequestMapping("/save")
    public String save() {
        Article a = new Article();
        a.setId("1");
        a.setSourceName("a jerk");
        a.setUrl("www.yourmom.com");
        a.setTitle("nice");
        a.setImgUrl("www.nty.io");
        repo.save(a);
        return a.toString();
    }



    @GetMapping("/location")
    public List<LocationResponse> getLocations() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = Paths.get("src/main/java/com/bac/bacbackend/repository/Markers.JSON").toFile();

        return objectMapper.readValue(file, new TypeReference<>() {});
    }

    public static class LocationResponse {
        private double x;
        private double y;
        private String html;

        public LocationResponse() {}

        public double getX() { return x; }
        public void setX(double x) { this.x = x; }

        public double getY() { return y; }
        public void setY(double y) { this.y = y; }

        public String getHtml() { return html; }
        public void setHtml(String html) { this.html = html; }
    }
}