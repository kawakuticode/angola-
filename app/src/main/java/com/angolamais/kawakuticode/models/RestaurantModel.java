package com.angolamais.kawakuticode.models;

import android.graphics.Bitmap;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by russeliusernestius on 26/01/17.
 */

public class RestaurantModel {


    private String n_restaurant, price_range, telephone, adress, facebook_url, city;
    private List<String> type_food;
    private Bitmap img_rest;

    public RestaurantModel() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getN_restaurant() {
        return n_restaurant;
    }

    public void setN_restaurant(String n_restaurant) {
        this.n_restaurant = n_restaurant;
    }

    public String getPrice_range() {
        return price_range;
    }

    public void setPrice_range(String price_range) {
        this.price_range = price_range;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getFacebook_url() {
        return facebook_url;
    }

    public void setFacebook_url(String facebook_url) {
        this.facebook_url = facebook_url;
    }

    public List <String> getType_food() {
        return type_food;
    }

    public void setType_food(List<String> type_food) {
        this.type_food = type_food;
    }

    public String getType_food_string() {

        String result = "";
        ListIterator<String> it = getType_food().listIterator();
        while (it.hasNext()) {

            if (!it.hasNext()) {
                result += it.next();

            } else {
                result += it.next() + " , ";

            }
        }
        return result;
    }

    public Bitmap getImg_rest() {
        return img_rest;
    }

    public void setImg_rest(Bitmap img_rest) {
        this.img_rest = img_rest;
    }

    @Override
    public String toString() {
        return "RestaurantModel{" +
                "n_restaurant='" + n_restaurant + '\'' +
                ", price_range='" + price_range + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adress='" + adress + '\'' +
                ", facebook_url='" + facebook_url + '\'' +
                ", type_food=" + type_food +
                ", img_rest=" + img_rest +
                '}';
    }


}
