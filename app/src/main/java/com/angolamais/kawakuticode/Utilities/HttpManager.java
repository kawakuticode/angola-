package com.angolamais.kawakuticode.Utilities;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by russeliusernestius on 10/02/17.
 */

public class HttpManager {

    public static String getDataFromWebService(String url_content) {

        OkHttpClient client = new OkHttpClient();
        Response response = null;


        try {
            Request request = new Request.Builder().url(url_content).build();
            response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (response != null) {
                try {
                    response.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
