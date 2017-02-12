package com.angolamais.kawakuticode.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.Utilities.AngolaMaisUtilities;
import com.angolamais.kawakuticode.angola.Listen_Radio_activity;
import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.RadioModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by russeliusernestius on 25/01/17.
 */

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioHolder> {

    private CardView radio_card_view;
    // Store the context for easy access
    private Context mContext;
    private List<RadioModel> radio_list;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public RadioAdapter(List<RadioModel> radio_data, Context context) {

        this.mContext = context;
        this.radio_list = radio_data;
        this.imageLoader.init(AngolaMaisUtilities.configuratioImageLoader(context).build());

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public RadioAdapter.RadioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_item, parent, false);
        return new RadioHolder(view, this.getContext());
    }

    @Override
    public void onBindViewHolder(RadioAdapter.RadioHolder holder, int position) {
        holder.radio_name.setText(radio_list.get(position).getRadio_name());
        holder.intro_info.setText(radio_list.get(position).getIntro_message());

        if (radio_list.get(position).getRadio_thumbnail() != null) {
            holder.radio_logo.setImageBitmap(radio_list.get(position).getRadio_thumbnail());
        } else {
            imageLoader.displayImage(radio_list.get(position).getRadio_img_url(), holder.radio_logo);
        }
    }

    @Override
    public int getItemCount() {
        return radio_list.size();
    }

    public class RadioHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView radio_name;
        public TextView intro_info;
        public ImageView radio_logo;
        private Context context;


        public RadioHolder(View v, Context context) {
            super(v);

            radio_name = (TextView) v.findViewById(R.id.radio_text);
            intro_info = (TextView) v.findViewById(R.id.intro_radio_text);
            radio_logo = (ImageView) v.findViewById(R.id.coverRadioImageView);
            this.context = context;
            // Attach a click listener to the entire row view
            v.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                RadioModel radio = radio_list.get(position);
                /// / We can access the data within the views
                Intent listen_radio_intent = new Intent(this.context, Listen_Radio_activity.class);
                listen_radio_intent.putExtra("radio", radio);
                this.context.startActivity(listen_radio_intent);
            }
        }
    }
}
