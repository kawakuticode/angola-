package com.angolamais.kawakuticode.Utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;
import java.util.Random;

/**
 * Created by russeliusernestius on 01/02/17.
 */

public class AngolaMaisUtilities {


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
}
