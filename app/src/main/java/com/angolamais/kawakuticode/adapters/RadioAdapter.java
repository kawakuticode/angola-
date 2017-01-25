package com.angolamais.kawakuticode.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.angolamais.kawakuticode.models.RadioModel;

import java.util.List;

/**
 * Created by russeliusernestius on 25/01/17.
 */

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioHolder> {
    private List<RadioModel> radio_list;


    public static class RadioHolder extends RecyclerView.ViewHolder {

        public RadioHolder(View v) {
            super(v);

        }
    }

    @Override
    public RadioAdapter.RadioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RadioAdapter.RadioHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
