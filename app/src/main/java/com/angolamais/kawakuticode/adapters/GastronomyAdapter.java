package com.angolamais.kawakuticode.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.FoodModel;

import java.util.List;

/**
 * Created by russeliusernestius on 26/01/17.
 */

public class GastronomyAdapter extends RecyclerView.Adapter<GastronomyAdapter.GastronomyHolder> {

    List<FoodModel> food_list;

    public GastronomyAdapter(List<FoodModel> food_list) {
        this.food_list = food_list;
    }

    @Override
    public GastronomyAdapter.GastronomyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gastronomy_item, parent, false
        );
        return new GastronomyHolder(view);
    }

    @Override
    public void onBindViewHolder(GastronomyAdapter.GastronomyHolder holder, int position) {
        holder.food_name.setText(food_list.get(position).getDish_name());
        holder.n_people.setText(food_list.get(position).getNumber_people());
        holder.time_preparation.setText(food_list.get(position).getTime_preparation());
        holder.food_img.setImageBitmap(food_list.get(position).getDish_img());

    }

    @Override
    public int getItemCount() {

        return food_list.size();
    }

    public static class GastronomyHolder extends RecyclerView.ViewHolder {
        public TextView food_name, n_people, time_preparation;
        public ImageView food_img;


        public GastronomyHolder(View itemView) {
            super(itemView);

            food_name = (TextView) itemView.findViewById(R.id.food_text);
            n_people = (TextView) itemView.findViewById(R.id.n_people_text);
            time_preparation = (TextView) itemView.findViewById(R.id.time_preparation_text);
            food_img = (ImageView) itemView.findViewById(R.id.coverFoodImageView);


        }
    }
}
