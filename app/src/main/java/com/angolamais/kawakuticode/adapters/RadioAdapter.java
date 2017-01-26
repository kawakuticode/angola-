package com.angolamais.kawakuticode.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.RadioModel;

import java.util.List;

/**
 * Created by russeliusernestius on 25/01/17.
 */

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioHolder> {
    private List<RadioModel> radio_list;

    public RadioAdapter(List<RadioModel> radio_data) {
        this.radio_list = radio_data;
    }


    public static class RadioHolder extends RecyclerView.ViewHolder {
        public TextView radio_name;
        public TextView intro_info;
        public ImageView radio_logo;


        public RadioHolder(View v) {
            super(v);

            radio_name = (TextView) v.findViewById(R.id.radio_text);
            intro_info = (TextView) v.findViewById(R.id.intro_radio_text);
            radio_logo = (ImageView) v.findViewById(R.id.coverRadioImageView);

        }
    }

    @Override
    public RadioAdapter.RadioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_item, parent, false);
        return new RadioHolder(view);
    }

    @Override
    public void onBindViewHolder(RadioAdapter.RadioHolder holder, int position) {
        holder.radio_name.setText(radio_list.get(position).getRadio_name());
        holder.radio_logo.setImageBitmap(radio_list.get(position).getRadio_thumbnail());
        holder.intro_info.setText(radio_list.get(position).getIntro_message());
    }

    @Override
    public int getItemCount() {
        return radio_list.size();
    }
}
