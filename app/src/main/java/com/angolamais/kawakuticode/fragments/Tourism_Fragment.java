package com.angolamais.kawakuticode.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.angolamais.kawakuticode.adapters.TourismAdapter;
import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.TourismModel;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TOURISM_URL_API = "https://angolamaiswebservice.herokuapp.com/tourism";
    private static final String TOURISM_URL_API_LOCAL = "http://10.0.2.2:8080/angolamaiswebservice/tourism";
    private RecyclerView recyclerView;
    private LinearLayoutManager gridLayoutManager;
    private TourismAdapter t_adapter;
    private List<TourismModel> tourism_data ;

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
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tourism_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tourism_Fragment newInstance(String param1, String param2) {
        Tourism_Fragment fragment = new Tourism_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Tourism Atractions In Angola");
        pd = new ProgressDialog(getContext());
        pd.setProgressStyle(0);
        pd.setTitle("Loading....... ");



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tourism_card, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.cardView_tourism);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager.setOrientation(gridLayoutManager.VERTICAL);
        tourism_data = new ArrayList<>();

        (new Load_tourism_data_from_webservice()).execute();
        t_adapter = new TourismAdapter(tourism_data, this.getContext());

        recyclerView.setAdapter(t_adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        return view;


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

    private List<String> load_galery_links(JSONArray ing) {
        List<String> galery_links = new ArrayList<>();
        try {
            for (int x = 0; x < ing.length(); x++) {

                galery_links.add(ing.getString(x));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return galery_links;
    }

    private ArrayList<TourismModel> load_tourism_data_on_array(JSONArray json_array) {

        ArrayList<TourismModel> tmp_list = new ArrayList<TourismModel>();

        for (int i = 0; i < json_array.length(); i++) {

            TourismModel tour_tmp = new TourismModel();
            try {
                JSONObject obj = json_array.getJSONObject(i);

                tour_tmp.setAtraction_name(obj.getString("atraction_name"));
                tour_tmp.setCity(obj.getString("city"));
                tour_tmp.setInfo(obj.getString("info"));
                tour_tmp.setLocation(obj.getString("location"));
                tour_tmp.setGallery_images(load_galery_links(obj.getJSONArray("t_gallery")));

                URL url1 = new URL(obj.getString("img_url"));

                Bitmap bmp = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                tour_tmp.setTour_thumbnail(bmp);

                tmp_list.add(tour_tmp);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tmp_list;
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

    private class Load_tourism_data_from_webservice extends AsyncTask<String, Void, List<TourismModel>> {


        @Override
        protected List<TourismModel> doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            //  Request request = new Request.Builder().url(TOURISM_URL_API).build();

            Request request = new Request.Builder().url(TOURISM_URL_API_LOCAL).build();
            try {
                Response response = client.newCall(request).execute();
                JSONArray array_json = new JSONArray(response.body().string());

                tourism_data.addAll(load_tourism_data_on_array(array_json));


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return tourism_data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(List<TourismModel> tourism_data_result) {
            super.onPostExecute(tourism_data_result);
            if (tourism_data_result.size() != 0) {
                t_adapter.notifyDataSetChanged();
                pd.dismiss();
            } else if (tourism_data_result.size() == 0) {
                pd.dismiss();
                Toast.makeText(getContext(), "No Tour places found " + tourism_data_result.size() + "", Toast.LENGTH_LONG).show();
            }
            pd.dismiss();
        }

    }


}

