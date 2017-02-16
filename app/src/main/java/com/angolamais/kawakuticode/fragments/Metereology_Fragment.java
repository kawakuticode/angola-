package com.angolamais.kawakuticode.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.zetterstrom.com.forecast.ForecastClient;
import android.zetterstrom.com.forecast.ForecastConfiguration;
import android.zetterstrom.com.forecast.models.DataPoint;
import android.zetterstrom.com.forecast.models.Forecast;

import com.angolamais.kawakuticode.Utilities.AMUtilities;
import com.angolamais.kawakuticode.Utilities.ForecastCallback;
import com.angolamais.kawakuticode.adapters.CurrentMetereoAdapter;
import com.angolamais.kawakuticode.adapters.DailyMetereoAdapter;
import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.City;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Metereology_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Metereology_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class Metereology_Fragment extends Fragment {


    private static String FORECAST_API_KEY = "e8d4660cb3aee3ce052434630b19b03b";

    private OnFragmentInteractionListener mListener;
    private RecyclerView current_metereo_view, daily_metereo_view;
    private DailyMetereoAdapter dailyMetereoAdapter;
    private CurrentMetereoAdapter currentMetereoAdapter;


    @Nullable
    private Forecast mForecast;


    public Metereology_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Metereology_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Metereology_Fragment newInstance(String param1, String param2) {
        Metereology_Fragment fragment = new Metereology_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ForecastConfiguration configuration =
                new ForecastConfiguration.Builder(FORECAST_API_KEY).build();
        ForecastClient.create(configuration);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.metereo_card, container, false);


        current_metereo_view = (RecyclerView) view.findViewById(R.id.current_metereo_view);
        daily_metereo_view = (RecyclerView) view.findViewById(R.id.daily_metereo_view);


        City city = AMUtilities.getCitiesValues().get("Huambo");
        // Log.d(" //// ", city.getCity_name());
        getForecastFrag(city);


        return view;
    }


    private void getForecastFrag(final City city) {


        AMUtilities.getForecast(city, new ForecastCallback() {

            @Override
            public void onForecastSuccess(Forecast forecast) {
                mForecast = forecast;
                showForecast(city);
            }

            @Override
            public void onForecastError(@Nullable Throwable throwable) {
                showError();
            }
        });
    }

    private void showForecast(City city) {
        if (mForecast != null) {

            if (mForecast.getCurrently() != null) {

                DataPoint dataPoint = mForecast.getCurrently();
                LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

                currentMetereoAdapter = new CurrentMetereoAdapter(city, dataPoint, this.getContext());
                current_metereo_view.setLayoutManager(verticalLayoutManagaer);
                current_metereo_view.setAdapter(currentMetereoAdapter);

                ArrayList<DataPoint> dailyDataPoints = mForecast.getDaily().getDataPoints();
                dailyMetereoAdapter = new DailyMetereoAdapter(dailyDataPoints, this.getContext());
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
                daily_metereo_view.setLayoutManager(horizontalLayoutManagaer);
                daily_metereo_view.setAdapter(dailyMetereoAdapter);
                Snackbar.make(getView(), "Updated info", Toast.LENGTH_SHORT).show();

            }

        }

    }


    private void showError() {
        Snackbar.make(getView(), "Error loading info ", Toast.LENGTH_SHORT).show();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.metereology_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        City city = new City();
        // Log.d(" //// ", city.getCity_name());

        switch (item.getItemId()) {
            case R.id.bengo:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.benguela:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.bie:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.cabinda:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.ccubango:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.knorte:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.ksul:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.cunene:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.huambo:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.huila:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.luanda:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.lnorte:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.lsul:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.malange:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.moxico:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.namibe:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.uige:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;
            case R.id.zaire:
                city = AMUtilities.getCitiesValues().get(item.getTitle());
                getForecastFrag(city);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
