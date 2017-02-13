package com.angolamais.kawakuticode.angola;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angolamais.kawakuticode.models.RestaurantModel;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

public class RestaurantActivity extends AppCompatActivity implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final String PACKAGE_CHROME = "com.android.chrome";
    private static final String PACKAGE_MAPS = "com.google.android.apps.maps";
    RestaurantModel restaurant_item;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        initUIElements();


    }

    public void initUIElements() {

        restaurant_item = getIntent().getParcelableExtra("restaurant");

        HashMap<String, String> url_maps = new HashMap<String, String>();
        for (int i = 0; i < restaurant_item.getGallery_urls().size(); i++) {
            url_maps.put(i + "", restaurant_item.getGallery_urls().get(i));
        }
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("")
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView rest_name = (TextView) findViewById(R.id.restaurant);
        rest_name.setText(restaurant_item.getN_restaurant());

        TextView price_range = (TextView) findViewById(R.id.range_price);
        price_range.setText("price : " + restaurant_item.getPrice_range());

        TextView type_food = (TextView) findViewById(R.id.speciality);
        type_food.setText("food type : " + restaurant_item.getType_food_string());

        ImageView call_icon = (ImageView) findViewById(R.id.call_icon);
        call_icon.setOnClickListener(this);

        ImageView direction_icon = (ImageView) findViewById(R.id.direction_icon);
        direction_icon.setOnClickListener(this);

        ImageView internet_icon = (ImageView) findViewById(R.id.internet_icon);
        internet_icon.setOnClickListener(this);

        ImageView share_item = (ImageView) findViewById(R.id.share_icon);
        share_item.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.call_icon:

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(restaurant_item.getTelephone().trim())));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);

                break;
            case R.id.direction_icon:


                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + restaurant_item.getAdress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage(PACKAGE_MAPS);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                break;

            case R.id.internet_icon:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(restaurant_item.getFacebook_url().trim()));
                browserIntent.setPackage(PACKAGE_CHROME);
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(browserIntent);
                }


                break;
            case R.id.share_icon:
                Snackbar.make(v, "bonho nhovoasd ", Toast.LENGTH_SHORT);
           /*     Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send)));*/

                break;

           /* case R.id.toolbar:
                Toast.makeText(this, "bonhosdasd nhovoasd ", Toast.LENGTH_SHORT).show();
                break;*/
        }


    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


}