package com.example.first_assignment;

import java.sql.Timestamp;

public class CitizenRequest {

    Timestamp timestamp;
    Double latitude;
    Double longitude;
    String category;
    String description;
    String imagePath;

    public CitizenRequest(Double latitude, Double longitude,
                          String category, String description, String imagePath) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }
}
