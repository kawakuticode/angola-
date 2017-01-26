package com.angolamais.kawakuticode.angola;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.angolamais.kawakuticode.adapters.RestaurantAdapter;
import com.angolamais.kawakuticode.models.RestaurantModel;
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
 * {@link Restaurant_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Restaurant_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Restaurant_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private static final String RESTAURANT_URL_API = "https://angolamaiswebservice.herokuapp.com/restaurant";
    private static final String RESTAURANT_URL_API_LOCAL = "http://10.0.2.2:8080/angolamaiswebservice/restaurant";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RestaurantAdapter r_adapter;
    private List<RestaurantModel> restaurant_data;
    private ProgressDialog pd;
    public Restaurant_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Restaurant_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Restaurant_Fragment newInstance(String param1, String param2) {
        Restaurant_Fragment fragment = new Restaurant_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Restaurants In Angola");

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
        View view = inflater.inflate(R.layout.restaurant_card, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.card_view_restaurant);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        restaurant_data = new ArrayList<>();

        (new Load_restaurant_data_from_webservice()).execute();
        r_adapter = new RestaurantAdapter(restaurant_data);

        recyclerView.setAdapter(r_adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

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
    private List <String> load_type_of_food(JSONArray ing) {
        List<String> type_of_food = new ArrayList<>();
        try {
            for (int x = 0; x < ing.length(); x++) {

                type_of_food.add(ing.getString(x));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return type_of_food;
    }
    private ArrayList<RestaurantModel> load_restaurant_data_on_array(JSONArray json_array) {

        ArrayList<RestaurantModel> tmp_list = new ArrayList<RestaurantModel>();

        for (int i = 0; i < json_array.length(); i++) {

            RestaurantModel restaurant = new RestaurantModel();
            try {

                JSONObject obj = json_array.getJSONObject(i);

                restaurant.setN_restaurant(obj.getString("rest_name"));
                restaurant.setCity(obj.getString("city"));
                restaurant.setPrice_range(obj.getString("price_range"));
                restaurant.setTelephone(obj.getString("telephone"));
                restaurant.setAdress(obj.getString("address"));
                restaurant.setFacebook_url("facebook_url");
                restaurant.setType_food(load_type_of_food(obj.getJSONArray("type_of_food")));

                URL url1 = new URL(obj.getString("image_url"));

                Bitmap bmp = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                restaurant.setImg_rest(bmp);

                tmp_list.add(restaurant);

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


    private class Load_restaurant_data_from_webservice extends AsyncTask<String, Void, List<RestaurantModel>> {


        @Override
        protected List<RestaurantModel> doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            //  Request request = new Request.Builder().url(TOURISM_URL_API).build();

            Request request = new Request.Builder().url(RESTAURANT_URL_API_LOCAL).build();
            try {
                Response response = client.newCall(request).execute();
                JSONArray array_json = new JSONArray(response.body().string());

                Log.d("moddddd" , response.toString());
                Log.d("moddddd" , array_json.toString());

               restaurant_data.addAll(load_restaurant_data_on_array(array_json));


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return restaurant_data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(List <RestaurantModel> restaurant_data_result) {
            super.onPostExecute(restaurant_data_result);
            if (restaurant_data_result.size() != 0) {
                r_adapter.notifyDataSetChanged();
                pd.dismiss();
            } else if (restaurant_data_result.size() == 0) {

                Toast.makeText(getContext(), "No Restaurants places found " + restaurant_data_result.size() + "", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }

        }

    }


}
