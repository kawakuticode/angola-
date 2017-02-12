package com.angolamais.kawakuticode.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by russeliusernestius on 25/01/17.
 */

public class RadioModel implements Parcelable {
    public static final Parcelable.Creator<RadioModel> CREATOR
            = new Parcelable.Creator<RadioModel>() {

        @Override
        public RadioModel createFromParcel(Parcel source) {
            return new RadioModel(source);
        }

        @Override
        public RadioModel[] newArray(int size) {
            return new RadioModel[size];
        }
    };
    private String radio_name;
    private String intro_message;
    private String radio_url;
    private String radio_img_url;
    private Bitmap radio_thumbnail;


    public RadioModel() {
    }

    public RadioModel(Parcel in) {
        radio_name = in.readString();
        intro_message = in.readString();
        radio_url = in.readString();
        radio_img_url = in.readString();
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

    public Bitmap getRadio_thumbnail() {
        return radio_thumbnail;
    }

    public void setRadio_thumbnail(Bitmap radio_thumbnail) {
        this.radio_thumbnail = radio_thumbnail;
    }

    public String getRadio_img_url() {
        return radio_img_url;
    }

    public void setRadio_img_url(String radio_img_url) {
        this.radio_img_url = radio_img_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(radio_name);
        dest.writeString(intro_message);
        dest.writeString(radio_url);
        dest.writeString(radio_img_url);
    }

    @Override
    public String toString() {
        return "RadioModel{" +
                "radio_name='" + radio_name + '\'' +
                ", intro_message='" + intro_message + '\'' +
                ", radio_url='" + radio_url + '\'' +
                ", radio_img_url=" + radio_img_url +
                '}';
    }
}
