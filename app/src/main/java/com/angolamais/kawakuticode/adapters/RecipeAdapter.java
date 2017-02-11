package com.angolamais.kawakuticode.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.Utilities.AngolaMaisUtilities;
import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.FoodModel;

/**
 * Created by russeliusernestius on 02/02/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private FoodModel food_recipe;

    private Intent intent_input;

    public RecipeAdapter(FoodModel food_recipe, Intent intent_in) {
        this.food_recipe = food_recipe;
        this.intent_input = intent_in;
    }

    public FoodModel getFood_recipe() {
        return food_recipe;
    }

    public void setFood_recipe(FoodModel food_recipe) {
        this.food_recipe = food_recipe;
    }

    @Override
    public RecipeAdapter.RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testing, parent, false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeHolder holder, int position) {

        holder.time_clock.setText(getFood_recipe().getTime_preparation());
        holder.food_title.setText(getFood_recipe().getDish_name());

        holder.full_recipe.setText(AngolaMaisUtilities.recipeDisplay(getFood_recipe().getIngridients(), getFood_recipe().getPreparation_text()));

        holder.clock_image.setImageResource(R.drawable.ic_time_cook);
        holder.food_image.setImageBitmap(AngolaMaisUtilities.getBitmapFromIntent(intent_input));


    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class RecipeHolder extends RecyclerView.ViewHolder {

        ImageView food_image, clock_image;
        TextView time_clock, food_title, full_recipe;


        public RecipeHolder(View itemView) {
            super(itemView);
            time_clock = (TextView) itemView.findViewById(R.id.time_cooki);
            food_title = (TextView) itemView.findViewById(R.id.food_title);
            full_recipe = (TextView) itemView.findViewById(R.id.full_recipe);
            full_recipe.setMovementMethod(new ScrollingMovementMethod());
            food_image = (ImageView) itemView.findViewById(R.id.food_image);
            clock_image = (ImageView) itemView.findViewById(R.id.clock_img);
        }


    }
}
