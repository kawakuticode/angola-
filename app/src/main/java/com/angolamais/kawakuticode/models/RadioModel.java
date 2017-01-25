package com.angolamais.kawakuticode.models;

import android.graphics.Bitmap;

/**
 * Created by russeliusernestius on 25/01/17.
 */

public class RadioModel {

    private String radio_name, intro_message , radio_url ;
    private Bitmap tour_thumbnail;

    public RadioModel() {
    }


    public String getRadio_name() {
        return radio_name;
    }

    public void setRadio_name(String radio_name) {
        this.radio_name = radio_name;
    }

    public String getIntro_message() {
        return intro_message;
    }

    public void setIntro_message(String intro_message) {
        this.intro_message = intro_message;
    }

    public String getRadio_url() {
        return radio_url;
    }

    public void setRadio_url(String radio_url) {
        this.radio_url = radio_url;
    }

    public Bitmap getTour_thumbnail() {
        return tour_thumbnail;
    }

    public void setTour_thumbnail(Bitmap tour_thumbnail) {
        this.tour_thumbnail = tour_thumbnail;
    }


    @Override
    public String toString() {
        return "RadioModel{" +
                "radio_name='" + radio_name + '\'' +
                ", intro_message='" + intro_message + '\'' +
                ", radio_url='" + radio_url + '\'' +
                ", tour_thumbnail=" + tour_thumbnail +
                '}';
    }
}
