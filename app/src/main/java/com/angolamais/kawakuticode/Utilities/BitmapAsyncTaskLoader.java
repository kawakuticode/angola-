package com.angolamais.kawakuticode.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by russeliusernestius on 07/02/17.
 */

public class BitmapAsyncTaskLoader extends AsyncTask<String, Void, Bitmap> {

    private ProgressDialog pb;
    private ImageView view;
    private Context mContext;


    public BitmapAsyncTaskLoader(ImageView myView, Context context) {
        this.view = myView;
        this.mContext = context;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {

            URL url1 = new URL(params[0]);
            URLConnection con = url1.openConnection();
            bitmap = BitmapFactory.decodeStream(con.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onPreExecute() {

        pb = new ProgressDialog(this.mContext);
        pb.setMessage("Downloading content ......");
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setIndeterminate(false);
        pb.setCancelable(true);
        pb.show();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        view.setImageBitmap(bitmap);
        pb.dismiss();

    }


}
