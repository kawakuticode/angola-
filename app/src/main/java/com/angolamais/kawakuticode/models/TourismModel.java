package com.angolamais.kawakuticode.models;

import android.graphics.Bitmap;

/**
 * Created by russeliusernestius on 24/01/17.
 */

public class TourismModel {
    private String atraction_name;
    private String city;
    private String info;
    private String location;
    private Bitmap tour_thumbnail;

    public TourismModel() {
    }

    public String getAtraction_name() {
        return atraction_name;
    }

    public void setAtraction_name(String atraction_name) {
        this.atraction_name = atraction_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Bitmap getTour_thumbnail() {
        return tour_thumbnail;
    }

    public void setTour_thumbnail(Bitmap tour_thumbnail) {
        this.tour_thumbnail = tour_thumbnail;
    }

    @Override
    public String toString() {
        return "TourismModel{" +
                "atraction_name='" + atraction_name + '\'' +
                ", city='" + city + '\'' +
                ", info='" + info + '\'' +
                ", location='" + location + '\'' +
                ", tour_thumbnail=" + tour_thumbnail +
                '}';
    }
}
