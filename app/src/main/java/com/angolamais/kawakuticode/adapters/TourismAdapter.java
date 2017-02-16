package com.angolamais.kawakuticode.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.Utilities.AMUtilities;
import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.angola.TourismActivity;
import com.angolamais.kawakuticode.models.TourismModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static com.angolamais.kawakuticode.angola.R.id.coverImageView;

/**
 * Created by russeliusernestius on 24/01/17.
 */

public class TourismAdapter extends RecyclerView.Adapter<TourismAdapter.TourismHolder> {

    List<TourismModel> tourism_items;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;

    public TourismAdapter(List<TourismModel> list, Context context) {

        this.tourism_items = list;
        this.context = context;
        this.imageLoader.init(AMUtilities.configuratioImageLoader(context).build());

    }


    @Override
    public TourismHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tourism_item, parent, false);
        return new TourismHolder(view, this.getContext());
    }

    @Override
    public void onBindViewHolder(TourismHolder holder, int position) {

        holder.atraction_name.setText(tourism_items.get(position).getAtraction_name());
        holder.city.setText(tourism_items.get(position).getCity());
        holder.likeImageView.setTag(R.drawable.ic_like);
        holder.shareImageView.setTag(R.drawable.ic_share);
        if (tourism_items.get(position).getTour_thumbnail() != null) {
            holder.img.setImageBitmap(tourism_items.get(position).getTour_thumbnail());
        } else {
            List<String> gallery_images = tourism_items.get(position).getGallery_images();
            int choosed_image = AMUtilities.getRandomNumberInRange(0, gallery_images.size() - 1);
            imageLoader.displayImage(gallery_images.get(choosed_image), holder.img);
        }


    }

    @Override
    public int getItemCount() {
        return tourism_items.size();
    }


    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    public class TourismHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView atraction_name;
        private TextView city;
        private ImageView img;
        private ImageView likeImageView;
        private ImageView shareImageView;
        private Context mcontext;


        public TourismHolder(View v, Context tcontext) {
            super(v);
            img = (ImageView) v.findViewById(coverImageView);
            atraction_name = (TextView) v.findViewById(R.id.atraction_name);
            city = (TextView) v.findViewById(R.id.city_text);
            likeImageView = (ImageView) v.findViewById(R.id.likeImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
            this.mcontext = tcontext;
            likeImageView.setOnClickListener(this);
            shareImageView.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.likeImageView:

                    likeImageView.setTag(R.drawable.ic_liked);
                    likeImageView.setImageResource(R.drawable.ic_liked);

                    break;
                case R.id.shareImageView:

                    Log.d("to be developed ", R.id.shareImageView + "");

                     /* Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(coverImageView.getId())
                            + '/' + "drawable" + '/' + getResources().getResourceEntryName((int) coverImageView.getTag()));

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    shareIntent.setType("image/jpeg");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));*/
                    break;

                default:

                    int position = getAdapterPosition(); // gets item position

                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it

                        TourismModel tourism_item = tourism_items.get(position);
                        /// / We can access the data within the views
                        Intent tourism_intent = new Intent(this.mcontext, TourismActivity.class);
                        tourism_intent.putExtra("tourism_item", tourism_item);

                        this.mcontext.startActivity(tourism_intent);


                        // Snackbar.make(v, "Buffering Tourism Content ... ", Snackbar.LENGTH_LONG).show();
                        //

                    }
            }
        }
    }
}