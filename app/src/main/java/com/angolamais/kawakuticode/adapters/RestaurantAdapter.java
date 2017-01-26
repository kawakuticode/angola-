package com.angolamais.kawakuticode.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.RestaurantModel;

import java.util.List;

/**
 * Created by russeliusernestius on 26/01/17.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder> {
    private List<RestaurantModel> list_restaurant;

    public RestaurantAdapter(List<RestaurantModel> restaurant_data) {
        this.list_restaurant = restaurant_data;
    }


    @Override
    public RestaurantAdapter.RestaurantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.RestaurantHolder holder, int position) {
        holder.rest_name.setText(list_restaurant.get(position).getN_restaurant());
        holder.rest_city.setText(list_restaurant.get(position).getCity());
        holder.rest_img.setImageBitmap(list_restaurant.get(position).getImg_rest());
        holder.food_type.setText(list_restaurant.get(position).getType_food_string());
    }

    @Override
    public int getItemCount() {
        return list_restaurant.size();
    }

    public static class RestaurantHolder extends RecyclerView.ViewHolder {
        public TextView rest_name, rest_city, food_type;
        public ImageView rest_img;


        public RestaurantHolder(View itemView) {
            super(itemView);

            rest_name = (TextView) itemView.findViewById(R.id.rest_name);
            rest_city = (TextView) itemView.findViewById(R.id.city_rest);
            food_type = (TextView) itemView.findViewById(R.id.food_type_text);
            rest_img = (ImageView) itemView.findViewById(R.id.coverRestImageView);

        }
    }
}
