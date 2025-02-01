package com.bac.bacbackend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

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