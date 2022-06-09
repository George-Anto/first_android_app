package com.example.first_assignment;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CitizenRequest {

    private String uid;
    private String date;
    private String time;
    private Double latitude;
    private Double longitude;
    private String category;
    private String description;
    private String imagePath;

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

    public  CitizenRequest() {}

    public Double getLatitude() { return latitude; }

    public Double getLongitude() { return longitude; }

    public String getCategory() { return category; }

    public String getDescription() { return description; }

    public String getImagePath() { return imagePath; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public String getUid() { return uid; }

    @Override
    public String toString() {
        return "CitizenRequest{" +
                "uid='" + uid + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
