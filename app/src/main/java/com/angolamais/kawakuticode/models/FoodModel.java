package com.angolamais.kawakuticode.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by russeliusernestius on 26/01/17.
 */

public class FoodModel {
    private String dish_name;
    private Bitmap dish_img;
    private String time_preparation;
    private String number_people;

    private List<String> ingridients;
    private String preparation_text;

    public FoodModel() {
    }


    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public Bitmap getDish_img() {
        return dish_img;
    }

    public void setDish_img(Bitmap dish_img) {
        this.dish_img = dish_img;
    }

    public String getTime_preparation() {
        return time_preparation;
    }

    public void setTime_preparation(String time_preparation) {
        this.time_preparation = time_preparation;
    }

    public String getNumber_people() {
        return number_people;
    }

    public void setNumber_people(String number_people) {
        this.number_people = number_people;
    }

    public List<String> getIngridients() {
        return ingridients;
    }

    public void setIngridients(List<String> ingridients) {
        this.ingridients = ingridients;
    }

    public String getPreparation_text() {
        return preparation_text;
    }

    public void setPreparation_text(String preparation_text) {
        this.preparation_text = preparation_text;
    }

    @Override
    public String toString() {
        return "FoodModel{" +
                "dish_name='" + dish_name + '\'' +
                ", dish_img=" + dish_img +
                ", time_preparation='" + time_preparation + '\'' +
                ", number_people='" + number_people + '\'' +
                ", ingridients=" + ingridients +
                ", preparation_text='" + preparation_text + '\'' +
                '}';
    }
}
