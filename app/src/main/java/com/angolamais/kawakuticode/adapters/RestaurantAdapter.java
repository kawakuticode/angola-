package com.angolamais.kawakuticode.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.Utilities.AngolaMaisUtilities;
import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.angola.RestaurantActivity;
import com.angolamais.kawakuticode.models.RestaurantModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by russeliusernestius on 26/01/17.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder> {
    List<RestaurantModel> list_restaurant;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();


    public RestaurantAdapter(List<RestaurantModel> restaurant_data, Context context) {
        this.context = context;
        this.list_restaurant = restaurant_data;
        this.imageLoader.init(AngolaMaisUtilities.configuratioImageLoader(context).build());
    }

    public Context getContext() {
        return context;
    }

    @Override
    public RestaurantAdapter.RestaurantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantHolder(view, this.getContext());
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.RestaurantHolder holder, int position) {

        holder.rest_name.setText(list_restaurant.get(position).getN_restaurant());
        holder.rest_city.setText(list_restaurant.get(position).getCity());
        holder.rest_img.setImageBitmap(list_restaurant.get(position).getImg_rest());
        holder.food_type.setText(list_restaurant.get(position).getType_food_string());

        if (list_restaurant.get(position).getImg_rest() != null) {
            holder.rest_img.setImageBitmap(list_restaurant.get(position).getImg_rest());
        } else {
            List<String> gallery_images = list_restaurant.get(position).getGallery_urls();
            int choosed_image_for_rest = AngolaMaisUtilities.getRandomNumberInRange(0, gallery_images.size() - 1);
            imageLoader.displayImage(gallery_images.get(choosed_image_for_rest), holder.rest_img);
        }
    }

    @Override
    public int getItemCount() {
        return list_restaurant.size();
    }

    public class RestaurantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rest_name, rest_city, food_type;
        public ImageView rest_img;
        public Context context;


        public RestaurantHolder(View itemView, Context hcontext) {
            super(itemView);

            rest_name = (TextView) itemView.findViewById(R.id.rest_name);
            rest_city = (TextView) itemView.findViewById(R.id.city_rest);
            food_type = (TextView) itemView.findViewById(R.id.food_type_text);
            rest_img = (ImageView) itemView.findViewById(R.id.coverRestImageView);
            this.context = hcontext;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                RestaurantModel rest = list_restaurant.get(position);
                /// / We can access the data within the views
                Intent food_intent = new Intent(this.context, RestaurantActivity.class);
                food_intent.putExtra("restaurant", rest);

                this.context.startActivity(food_intent);
        }
    }
    }
}
