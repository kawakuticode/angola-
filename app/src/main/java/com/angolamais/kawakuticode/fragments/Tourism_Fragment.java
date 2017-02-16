package com.angolamais.kawakuticode.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angolamais.kawakuticode.Utilities.HttpManager;
import com.angolamais.kawakuticode.Utilities.JsonParsers;
import com.angolamais.kawakuticode.adapters.TourismAdapter;
import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.TourismModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tourism_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tourism_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tourism_Fragment extends Fragment {

    private static final String TOURISM_URL_API = "https://angolamaiswebservice.herokuapp.com/tourism";
    private static final String TOURISM_URL_API_LOCAL = "http://10.0.2.2:8080/angolamaiswebservice/tourism";
    private RecyclerView recyclerView;
    private LinearLayoutManager gridLayoutManager;
    private TourismAdapter t_adapter;
    private List<TourismModel> tourism_spot_list = new ArrayList<>();

    private ProgressDialog pd;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tourism_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment Tourism_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tourism_Fragment newInstance(String param1, String param2) {
        Tourism_Fragment fragment = new Tourism_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Tourism Atractions In Angola");
        requestDataTourismFromWebService(TOURISM_URL_API_LOCAL);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tourism_card, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.cardView_tourism);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager.setOrientation(gridLayoutManager.VERTICAL);


        recyclerView.setAdapter(t_adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        return view;


    }

    private void requestDataTourismFromWebService(String url_content) {

        MyTourismSpotAsyncTask task = new MyTourismSpotAsyncTask();
        task.execute(url_content);
    }

    protected void updateDisplay() {
        if (tourism_spot_list != null) {
            t_adapter = new TourismAdapter(tourism_spot_list, this.getContext());
            recyclerView.setAdapter(t_adapter);
            t_adapter.notifyDataSetChanged();

        } else {
            pd.dismiss();
            Snackbar.make(getView(), "No Tourism Spots  found ", Snackbar.LENGTH_LONG).show();
        }

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


    private class MyTourismSpotAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getContext());
            pd.setProgressStyle(0);
            pd.setTitle("Loading . . . . . . .");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpManager.getDataFromWebService(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String stringFromWebService) {
            tourism_spot_list = JsonParsers.tourismSpotsParser(stringFromWebService);
            Collections.shuffle(tourism_spot_list);
            updateDisplay();
            pd.dismiss();
        }
    }


}

