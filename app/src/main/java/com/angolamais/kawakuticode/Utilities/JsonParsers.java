package com.angolamais.kawakuticode.Utilities;

import com.angolamais.kawakuticode.models.FoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by russeliusernestius on 10/02/17.
 */

public class JsonParsers {

    //CONSTANTS FOR FOOD RECIPES
    private static final String DISH_NAME = "dish_name";
    private static final String NUMBER_OF_PEOPLE = "n_people";
    private static final String PREPARATION = "preparation";
    private static final String TIME_PREPARATION = "time_preparation";
    private static final String IMAGE_URL = "image_url";
    private static final String INGRIDIENTS = "ingridientes";


    public static List<FoodModel> getFoodRecipsParser(String content) {

        ArrayList<FoodModel> foodlist = new ArrayList<FoodModel>();

        try {
            JSONArray json_array = new JSONArray(content);

            for (int i = 0; i < json_array.length(); i++) {

                FoodModel food = new FoodModel();

                JSONObject obj = json_array.getJSONObject(i);

                food.setDish_name(obj.getString(DISH_NAME));
                food.setNumber_people(obj.getString(NUMBER_OF_PEOPLE));
                food.setPreparation_text(obj.getString(PREPARATION));
                food.setTime_preparation(obj.getString(TIME_PREPARATION));
                food.setUrlImg(obj.getString(IMAGE_URL));
                food.setIngridients(load_food_ingridientes(obj.getJSONArray(INGRIDIENTS)));

                foodlist.add(food);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return foodlist;
    }

    private static List<String> load_food_ingridientes(JSONArray ing_json_array) {

        List<String> list_ingridients = new ArrayList<>();
        try {
            for (int x = 0; x < ing_json_array.length(); x++) {

                list_ingridients.add(ing_json_array.getString(x));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list_ingridients;
    }
}