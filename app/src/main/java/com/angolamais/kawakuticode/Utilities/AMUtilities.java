package com.angolamais.kawakuticode.Utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.zetterstrom.com.forecast.ForecastClient;
import android.zetterstrom.com.forecast.models.Forecast;

import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.City;
import com.angolamais.kawakuticode.models.Coordinates;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by russeliusernestius on 01/02/17.
 */

public class AMUtilities {


    final static String[] DAYS_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static double MILES_UNITY = 1.609344;

    public static ImageLoaderConfiguration.Builder configuratioImageLoader(Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(cacheSize());
        config.tasksProcessingOrder(QueueProcessingType.FIFO);


        return config;

    }

    public static int cacheSize() {

        final int max_memory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cache_size = max_memory / 8;
        return cache_size;

    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public static Bitmap getBitmapFromIntent(Intent intent_input) {
        Bitmap bmp = null;
        try {
            byte[] byteArray = intent_input.getByteArrayExtra("bitmapbytes");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public static String recipeDisplay(List<String> input, String content) {

        String result = "Ingridients " + "\n";
        result += "\n";

        for (String i : input) {
            result += "* " + i + "\n";
        }
        result += "\n";
        result += "Preparation " + "\n";
        result += "\n";
        result += content;
        return result;
    }

    public static City StringToCoordinates(Provinces p) {

        City city = new City();
        Coordinates coordinates = new Coordinates();

        try {

            String[] coordinates_string = p.coordinates().split(",");
            coordinates.setLatitude(Double.parseDouble(coordinates_string[0]));
            coordinates.setLongitude(Double.parseDouble(coordinates_string[1]));
            city.setCoordinates(coordinates);
            city.setCity_name(p.name());

            //Log.d("City" , city.toString());
            return city;

        } catch (NumberFormatException n) {
            n.printStackTrace();
            return null;
        }

    }

    public static HashMap<String, City> getCitiesValues() {

        HashMap<String, City> contents = new HashMap<>();
        contents = new HashMap<>();

        for (Provinces p : Provinces.values()) {

            contents.put(p.name(), StringToCoordinates(p));

        }

        return contents;
    }

    public static void getForecast(City city, final ForecastCallback callback) {


        if (city != null) {

            float[] results = new float[1];
            double latitude = city.getCoordinates().getLatitude();
            double longitude = city.getCoordinates().getLongitude();

            ForecastClient.getInstance()
                    .getForecast(latitude, longitude, new Callback<Forecast>() {
                        @Override
                        public void onResponse(Call<Forecast> forecastCall, Response<Forecast> response) {
                            if (response.isSuccessful()) {

                                callback.onForecastSuccess(response.body());
                            } else {
                                callback.onForecastError(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<Forecast> forecastCall, Throwable t) {
                            callback.onForecastError(t);
                        }
                    });
        }
    }

    public static String convertFaranheitToCelsius(double faranheit_temp) {

        double temp = ((faranheit_temp - 32) * 5) / 9;
        return String.valueOf(truncateDecimal(temp, 1)) + "˚c";

    }

    public static String convertMilesToKilometres(double value) {

        double miles = value * MILES_UNITY;
        return String.valueOf(truncateDecimal(miles, 2)) + " Km/h";

    }

    public static String getDayOfWeek(int day_of_week) {
        return DAYS_WEEK[day_of_week - 1];
    }

    public static BigDecimal truncateDecimal(double x, int numberofDecimals) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }

    public static String shortWeatherState(String inputWeather) {
        switch (inputWeather) {
            case "clear-day":
                return "clear day";
            case "clear-night":
                return "clear night";
            case "partly-cloudy-day":
                return "partly cloudy";
            case "partly-cloudy-night":
                return "partly cloudy";
            default:
                return inputWeather;
        }
    }

    public static String buildNameAsResource(String inputWeather) {
        switch (inputWeather) {

            case "clear-day":
                return "clear_day";
            case "clear-night":
                return "clear_night";
            case "partly-cloudy-day":
                return "partly_cloudy_day";
            case "partly-cloudy-night":
                return "partly_cloudy_night";
            default:
                return inputWeather;
        }
    }

    public static int setIconSource(String text) {

        switch (text) {
            case "tornado":
                return R.drawable.tornado;
            case "thunderstorm":
                return R.drawable.thunderstorm;
            case "hail":
                return R.drawable.hail;
            case "cloudy":
                return R.drawable.cloudy;
            case "fog":
                return R.drawable.fog;
            case "wind":
                return R.drawable.wind;
            case "sleet":
                return R.drawable.sleet;
            case "snow":
                return R.drawable.snow;
            case "rain":
                return R.drawable.rain;
            case "clear_day":
                return R.drawable.clear_day;
            case "clear_night":
                return R.drawable.clear_night;
            case "partly_cloudy_day":
                return R.drawable.partly_cloudy_day;
            case "partly_cloudy_night":
                return R.drawable.partly_cloudy_night;
            default:
                return R.drawable.clear_night;
        }
    }

    public static Bitmap setMyImageBitmap(int resId, Context mContext) {

        Drawable d = ResourcesCompat.getDrawable(mContext.getResources(), resId, null);

        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);

        return bitmap;
        //getCtx().getResources().getIdentifier("@drawable/partly_cloudy_day", "drawable", getCtx().getPackageName());
    }

    public static enum Provinces {
        Bengo,
        Benguela,
        Bie,
        Cabinda,
        CCubango,
        KNorte,
        KSul,
        Cunene,
        Huambo,
        Huila,
        Luanda,
        LNorte,
        LSul,
        Malange,
        Moxico,
        Namibe,
        Uige,
        Zaire,;

        String coordinates() {
            switch (this) {
                case Bengo:
                    return "-9.107625,13.687311";
                case Benguela:
                    return "-12.590516,13.416501";
                case Bie:
                    return "-12.401288,16.932937";
                case Cabinda:
                    return "-5.577520,12.192710";
                case CCubango:
                    return "-14.659567,17.698475";
                case KNorte:
                    return "-9.300590,14.913410";
                case KSul:
                    return "-11.359032,15.115740";
                case Cunene:
                    return "-16.751812,14.970269";
                case Huambo:
                    return "-12.773976,15.746854";
                case Huila:
                    return "-14.914004,13.504905";
                case Luanda:
                    return "-8.839988,13.289437";
                case LNorte:
                    return "-8.449118,20.717827";
                case LSul:
                    return "-10.286658,20.712246";
                case Malange:
                    return "-9.825118,16.912251";
                case Moxico:
                    return "-13.696924,19.864276";
                case Namibe:
                    return "-15.197832,12.157554";
                case Uige:
                    return "-7.609277,15.055239";
                case Zaire:
                    return "-4.038333,21.758664";
                default:
                    throw new AssertionError("Unknown operations " + this);
            }
        }

    }
}
