package com.bac.bacbackend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    @GetMapping("/location")
    public LocationResponse getLocation() {

        return new LocationResponse(48.8566, 2.3522);
    }

    public static class LocationResponse {
        private double locationX;
        private double locationY;

        public LocationResponse(double locationX, double locationY) {
            this.locationX = locationX;
            this.locationY = locationY;
        }

        public double getLocationX() {
            return locationX;
        }

        public void setLocationX(double locationX) {
            this.locationX = locationX;
        }

        public double getLocationY() {
            return locationY;
        }

        public void setLocationY(double locationY) {
            this.locationY = locationY;
        }
    }
}
