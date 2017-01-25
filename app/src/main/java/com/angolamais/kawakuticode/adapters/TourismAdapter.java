package com.angolamais.kawakuticode.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.TourismModel;

import java.util.ArrayList;
import java.util.List;

import static com.angolamais.kawakuticode.angola.R.id.coverImageView;

/**
 * Created by russeliusernestius on 24/01/17.
 */

public class TourismAdapter extends RecyclerView.Adapter<TourismAdapter.TourismHolder> {

    private List<TourismModel> list;


    public TourismAdapter( List <TourismModel> list) {
        this.list = list;
    }


    public static class TourismHolder extends RecyclerView.ViewHolder {

        public TextView atraction_name;
        public TextView city;
        public ImageView img;
        public ImageView likeImageView;
        public ImageView shareImageView;


        public TourismHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(coverImageView);
            atraction_name = (TextView) v.findViewById(R.id.atraction_name);
            city = (TextView) v.findViewById(R.id.city_text);
            likeImageView = (ImageView) v.findViewById(R.id.likeImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
            likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) likeImageView.getTag();
                    if (id == R.drawable.ic_like) {

                        likeImageView.setTag(R.drawable.ic_liked);
                        likeImageView.setImageResource(R.drawable.ic_liked);
                        // Toast.makeText(getActivity(), atraction_name.getText() + " added to favourites", Toast.LENGTH_SHORT).show();

                    } else {

                        likeImageView.setTag(R.drawable.ic_like);
                        likeImageView.setImageResource(R.drawable.ic_like);
                        // Toast.makeText(getActivity(), atraction_name.getText() + " removed from favourites", Toast.LENGTH_SHORT).show();

                    }
                }
            });
            shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getResources().getResourcePackageName(coverImageView.getId())
                            + '/' + "drawable" + '/' + getResources().getResourceEntryName((int) coverImageView.getTag()));

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    shareIntent.setType("image/jpeg");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));*/
                }
            });
        }
    }

    @Override
    public TourismHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tourism_item, parent, false);
        return new TourismHolder(view);
    }

    @Override
    public void onBindViewHolder(TourismHolder holder, int position) {

        holder.atraction_name.setText(list.get(position).getAtraction_name());
        holder.city.setText(list.get(position).getCity());
        holder.img.setImageBitmap(list.get(position).getTour_thumbnail());
        holder.likeImageView.setTag(R.drawable.ic_like);
        holder.shareImageView.setTag(R.drawable.ic_share);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}