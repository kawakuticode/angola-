package com.angolamais.kawakuticode.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by russeliusernestius on 14/02/17.
 */

public class City implements Parcelable {

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
    private String city_name;
    private Coordinates coordinates;

    public City() {
    }

    public City(String city_name, Coordinates coordinates) {
        this.city_name = city_name;
        this.coordinates = coordinates;
    }

    protected City(Parcel in) {
        this.city_name = in.readString();
        this.coordinates = in.readParcelable(Coordinates.class.getClassLoader());
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "City{" +
                "city_name='" + city_name + '\'' +
                ", coordinates=" + coordinates.toString() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city_name);
        dest.writeParcelable(this.coordinates, flags);
    }
}
