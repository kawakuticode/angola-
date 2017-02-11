package com.angolamais.kawakuticode.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by russeliusernestius on 24/01/17.
 */

public class TourismModel implements Parcelable {
    public static final Parcelable.Creator<TourismModel> CREATOR = new Parcelable.Creator<TourismModel>() {
        @Override
        public TourismModel createFromParcel(Parcel source) {
            return new TourismModel(source);
        }

        @Override
        public TourismModel[] newArray(int size) {
            return new TourismModel[size];
        }
    };
    private String atraction_name;
    private String city;
    private String info;
    private String location;
    private Bitmap tour_thumbnail;
    private List<String> gallery_images;

    public TourismModel() {
    }

    protected TourismModel(Parcel in) {
        this.atraction_name = in.readString();
        this.city = in.readString();
        this.info = in.readString();
        this.location = in.readString();
        //this.tour_thumbnail = in.readParcelable(Bitmap.class.getClassLoader());
        this.gallery_images = in.createStringArrayList();
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

    public List<String> getGallery_images() {
        return gallery_images;
    }

    public void setGallery_images(List<String> gallery_images) {
        this.gallery_images = gallery_images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.atraction_name);
        dest.writeString(this.city);
        dest.writeString(this.info);
        dest.writeString(this.location);
        //dest.writeParcelable(this.tour_thumbnail, flags);
        dest.writeStringList(this.gallery_images);
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
