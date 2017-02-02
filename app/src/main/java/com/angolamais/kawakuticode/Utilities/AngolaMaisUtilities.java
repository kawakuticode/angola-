package com.angolamais.kawakuticode.Utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.List;

/**
 * Created by russeliusernestius on 01/02/17.
 */

public class AngolaMaisUtilities {
    private Context utilicontext;

    public AngolaMaisUtilities() {
    }

    public AngolaMaisUtilities(Context utilicontext) {
        this.utilicontext = utilicontext;
    }

    public Context getUtilicontext() {
        return utilicontext;
    }

    public void setUtilicontext(Context utilicontext) {
        this.utilicontext = utilicontext;
    }


    public Bitmap getBitmapFromIntent(Intent intent_input) {
        Bitmap bmp = null;
        try {
            byte[] byteArray = intent_input.getByteArrayExtra("bitmapbytes");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public String recipeDisplay(List<String> input, String content) {

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
