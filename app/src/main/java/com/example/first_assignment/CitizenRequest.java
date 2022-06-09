package com.example.first_assignment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CitizenRequest {

    private final String uid;
    private final String date;
    private final String time;
    private final Double latitude;
    private final Double longitude;
    private final String category;
    private final String description;
    private final String imagePath;

    public CitizenRequest(String uid, Double latitude, Double longitude,
                          String category, String description, String imagePath) {
        this.uid = uid;
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Double getLatitude() { return latitude; }

    public Double getLongitude() { return longitude; }

    public String getCategory() { return category; }

    public String getDescription() { return description; }

    public String getImagePath() { return imagePath; }

    public String getDate() { return date; }

    public String getTime() { return time; }
}
