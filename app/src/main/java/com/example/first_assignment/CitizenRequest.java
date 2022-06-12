package com.example.first_assignment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//Class that represents the Requests the users send to our database
public class CitizenRequest {

    //The id of the user that send the request
    private String uid;
    private String date;
    private String time;
    //The coordinates of the incident
    private Double latitude;
    private Double longitude;
    //The address of the incident (is calculated based on the coordinates)
    private String locationAddress;
    private String category;
    private String description;
    //The firebase storage Access Token for the specific file
    //(we can use it to download and/or view the file)
    private String imagePath;

    //The Constructor
    public CitizenRequest(String uid, Double latitude, Double longitude, String locationAddress,
                          String category, String description, String imagePath) {
        this.uid = uid;
        //The date and time are calculated internally by the system when the Constructor is called
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        this.latitude = latitude;
        this.longitude = longitude;
        setLocationAddress(locationAddress);
        this.category = category;
        this.description = description;
        this.imagePath = imagePath;
    }

    public  CitizenRequest() {}

    //Getters of the fields of the Requests
    public Double getLatitude() { return latitude; }

    public Double getLongitude() { return longitude; }

    public String getCategory() { return category; }

    public String getDescription() { return description; }

    public String getImagePath() { return imagePath; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public String getUid() { return uid; }

    public String getLocationAddress() { return locationAddress; }

    //If we are not able to extract the address via the Geocoder (due to slow internet for instance)
    //We set an unknown address String
    public void setLocationAddress(String address) {
        if (address == null) {
            this.locationAddress = "Unknown Address";
            return;
        }
        this.locationAddress = address;
    }

    //The toString method, mainly used for debugging purposes for this project
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
