package com.angolamais.kawakuticode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.zetterstrom.com.forecast.models.DataPoint;

import com.angolamais.kawakuticode.Utilities.AMUtilities;
import com.angolamais.kawakuticode.angola.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by russeliusernestius on 15/02/17.
 */

public class DailyMetereoAdapter extends RecyclerView.Adapter<DailyMetereoAdapter.MyViewHolder> {


    private Context ctx;
    private Calendar calendar;
    private List<DataPoint> dailyContent;


    public DailyMetereoAdapter(List<DataPoint> horizontalList, Context ctx) {
        this.dailyContent = horizontalList;
        this.ctx = ctx;
        this.calendar = Calendar.getInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.metereo_daily, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        DataPoint dataPoint = dailyContent.get(position);
        getCalendar().setTime(dataPoint.getTime());

        String img_name = AMUtilities.buildNameAsResource(dataPoint.getIcon().getText());
        int iconResId = AMUtilities.setIconSource(img_name);

        holder.day_week.setText(AMUtilities.getDayOfWeek(getCalendar().get(Calendar.DAY_OF_WEEK)));
        holder.max_temp.setText(AMUtilities.convertFaranheitToCelsius(dataPoint.getTemperatureMax()));
        holder.min_temp.setText(AMUtilities.convertFaranheitToCelsius(dataPoint.getTemperatureMin()));
        holder.state_day_week.setText(AMUtilities.shortWeatherState(dataPoint.getIcon().getText()));
        if (holder.forecast_image != null) {

            holder.forecast_image.setImageBitmap(AMUtilities.setMyImageBitmap(iconResId, getCtx()));
        }


    }


    public Context getCtx() {
        return ctx;
    }

    public List<DataPoint> getDailyContent() {
        return dailyContent;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    public int getItemCount() {
        return dailyContent.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day_week, max_temp, min_temp, state_day_week;
        public ImageView forecast_image;

        public MyViewHolder(View view) {

            super(view);
            day_week = (TextView) view.findViewById(R.id.day_week);
            max_temp = (TextView) view.findViewById(R.id.max_tmp);
            min_temp = (TextView) view.findViewById(R.id.min_temp);
            state_day_week = (TextView) view.findViewById(R.id.state_day_week);
            forecast_image = (ImageView) view.findViewById(R.id.forecast_image);

        }
    }

}

