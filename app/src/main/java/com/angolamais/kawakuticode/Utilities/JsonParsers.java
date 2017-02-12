package com.angolamais.kawakuticode.Utilities;

import com.angolamais.kawakuticode.models.FoodModel;
import com.angolamais.kawakuticode.models.RadioModel;
import com.angolamais.kawakuticode.models.RestaurantModel;
import com.angolamais.kawakuticode.models.TourismModel;

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
    private static final String FOOD_IMAGE_URL = "image_url";
    private static final String INGRIDIENTS = "ingridientes";


    //CONSTANTS FOR RADIO
    private static final String RADIO_NAME = "radio_name";
    private static final String RADIO_URL = "radio_url";
    private static final String RADIO_IMG_URL = "img_url";
    private static final String RADIO_INTRO_MESSAGE = "intro_message";


    //CONSTANTS FOR TOURISMSPOTS

    private static final String SPOT_NAME = "atraction_name";
    private static final String CITY = "city";
    private static final String INFO = "info";
    private static final String LOCATION = "location";
    private static final String GALLERY = "t_gallery";


    //CONSTANTS FOR RESTAURANTS

    private static final String REST_NAME = "rest_name";
    private static final String REST_CITY = "city";
    private static final String PRICE_RANGE = "price_range";
    private static final String TELEPHONE = "telephone";
    private static final String ADRESS = "address";
    private static final String FACEBOOK_URL = "facebook_url";
    private static final String FOOD_TYPE = "type_of_food";
    private static final String GALLERY_URLs = "rest_gallery";


    public static List<FoodModel> foodRecipsParser(String content) {

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
                food.setUrlImg(obj.getString(FOOD_IMAGE_URL));
                food.setIngridients(load_array_content_property(obj.getJSONArray(INGRIDIENTS)));

                foodlist.add(food);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return foodlist;
    }


    public static List<RadioModel> radioStationsParser(String content) {

        ArrayList<RadioModel> radioList = new ArrayList<>();

        try {

            JSONArray json_array = new JSONArray(content);
            for (int i = 0; i < json_array.length(); i++) {

                RadioModel radio = new RadioModel();
                JSONObject obj = json_array.getJSONObject(i);

                radio.setRadio_name(obj.getString(RADIO_NAME));
                radio.setIntro_message(obj.getString(RADIO_INTRO_MESSAGE));
                radio.setRadio_url(obj.getString(RADIO_URL));
                radio.setRadio_img_url(obj.getString(RADIO_IMG_URL));
                radioList.add(radio);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return radioList;
    }

    public static List<TourismModel> tourismSpotsParser(String content) {

        ArrayList<TourismModel> tourism_spots_lists = new ArrayList<TourismModel>();
        try {
            JSONArray json_array = new JSONArray(content);

            for (int i = 0; i < json_array.length(); i++) {

                TourismModel tour_tmp = new TourismModel();
                JSONObject obj = json_array.getJSONObject(i);

                tour_tmp.setAtraction_name(obj.getString(SPOT_NAME));
                tour_tmp.setCity(obj.getString(CITY));
                tour_tmp.setInfo(obj.getString(INFO));
                tour_tmp.setLocation(obj.getString(LOCATION));
                tour_tmp.setGallery_images(load_array_content_property(obj.getJSONArray(GALLERY)));

                tourism_spots_lists.add(tour_tmp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tourism_spots_lists;
    }


    public static ArrayList<RestaurantModel> RestaurantsParser(String content) {

        ArrayList<RestaurantModel> restaurantList = new ArrayList<>();
        try {

            JSONArray json_array = new JSONArray(content);

            for (int i = 0; i < json_array.length(); i++) {

                JSONObject obj = json_array.getJSONObject(i);
                RestaurantModel rest = new RestaurantModel();

                rest.setN_restaurant(obj.getString(REST_NAME));
                rest.setCity(obj.getString(REST_CITY));
                rest.setPrice_range(obj.getString(PRICE_RANGE));
                rest.setTelephone(obj.getString(TELEPHONE));
                rest.setAdress(obj.getString(ADRESS));
                rest.setFacebook_url(FACEBOOK_URL);
                rest.setType_food(load_array_content_property(obj.getJSONArray(FOOD_TYPE)));
                rest.setGallery_urls(load_array_content_property(obj.getJSONArray(GALLERY_URLs)));

                restaurantList.add(rest);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return restaurantList;
    }

    private static List<String> load_array_content_property(JSONArray ing) {
        List<String> content_array = new ArrayList<>();
        try {
            for (int x = 0; x < ing.length(); x++) {

                content_array.add(ing.getString(x));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return content_array;
    }
}