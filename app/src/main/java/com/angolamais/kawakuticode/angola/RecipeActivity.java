package com.angolamais.kawakuticode.angola;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.Utilities.AngolaMaisUtilities;
import com.angolamais.kawakuticode.Utilities.BitmapAsyncTaskLoader;
import com.angolamais.kawakuticode.adapters.RecipeAdapter;
import com.angolamais.kawakuticode.models.FoodModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class RecipeActivity extends AppCompatActivity {
    ImageView food_main_image;
    TextView dish_name, time_cook, content_desc, ingridients;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecipeAdapter recipeAdapter;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*recyclerView = (RecyclerView) this.findViewById(R.id.card_view_recipe);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        FoodModel food_content = getIntent().getParcelableExtra("dish");

        recipeAdapter = new RecipeAdapter((FoodModel) food_content, getIntent());

        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);*/


        initUIElementsFood();


    }


    public void initUIElementsFood() {


        this.imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        food_main_image = (ImageView) findViewById(R.id.food_image);
        dish_name = (TextView) findViewById(R.id.food_title);
        time_cook = (TextView) findViewById(R.id.time_cooki);

        content_desc = (TextView) findViewById(R.id.full_recipe);
        content_desc.setMovementMethod(new ScrollingMovementMethod());


        FoodModel food_content = getIntent().getParcelableExtra("dish");



        dish_name.setText(food_content.getDish_name());
        time_cook.setText(food_content.getTime_preparation());
        content_desc.setText(AngolaMaisUtilities.recipeDisplay(food_content.getIngridients(), food_content.getPreparation_text()));
        // imageLoader.displayImage(food_content.getUrlImg(), food_main_image);
        new BitmapAsyncTaskLoader(food_main_image, this).execute(food_content.getUrlImg());

    }

}
