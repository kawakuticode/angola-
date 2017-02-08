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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.angolamais.kawakuticode.adapters.GastronomyAdapter;
import com.angolamais.kawakuticode.models.FoodModel;
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
 * {@link Gastronomy_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Gastronomy_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gastronomy_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String GASTRONOMY_URL_API = "https://angolamaiswebservice.herokuapp.com/gastronomy";
    private static final String GASTRONOMY_URL_API_LOCAL = "http://10.0.2.2:8080/angolamaiswebservice/gastronomy";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GastronomyAdapter g_adapter;
    private List<FoodModel> food_data;

    private ProgressDialog pd;

    private OnFragmentInteractionListener mListener;

    public Gastronomy_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Gastronomy_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Gastronomy_Fragment newInstance(String param1, String param2) {
        Gastronomy_Fragment fragment = new Gastronomy_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Gastronomy of Angola");
        pd = new ProgressDialog(getContext());
        pd.setProgressStyle(0);
        pd.setTitle("Loading . . . . . . .");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.gastronomy_card, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.card_view_gastronomy);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        food_data = new ArrayList<>();

        (new Load_gastronomy_data_from_webservice()).execute();
        g_adapter = new GastronomyAdapter(food_data, this.getContext());

        recyclerView.setAdapter(g_adapter);
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


    private List<String> load_ingridientes(JSONArray ing) {
        List<String> list_ingridients = new ArrayList<>();
        try {
            for (int x = 0; x < ing.length(); x++) {

                list_ingridients.add(ing.getString(x));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list_ingridients;
    }

    private ArrayList<FoodModel> load_food_data_on_array(JSONArray json_array) {

        ArrayList<FoodModel> tmp_list = new ArrayList<FoodModel>();

        for (int i = 0; i < json_array.length(); i++) {

            FoodModel food = new FoodModel();
            try {
                JSONObject obj = json_array.getJSONObject(i);

                food.setDish_name(obj.getString("dish_name"));
                food.setNumber_people(obj.getString("n_people"));
                food.setPreparation_text(obj.getString("preparation"));
                food.setTime_preparation(obj.getString("time_preparation"));
                food.setUrlImg(obj.getString("image_url"));

                food.setIngridients(load_ingridientes(obj.getJSONArray("ingridientes")));

                URL url1 = new URL(obj.getString("image_url"));

                Bitmap bmp = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                food.setDish_img(bmp);


                tmp_list.add(food);

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

    private class Load_gastronomy_data_from_webservice extends AsyncTask<String, Void, List<FoodModel>> {


        @Override
        protected List<FoodModel> doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            //  Request request = new Request.Builder().url(TOURISM_URL_API).build();

            Request request = new Request.Builder().url(GASTRONOMY_URL_API_LOCAL).build();
            try {
                Response response = client.newCall(request).execute();
                JSONArray array_json = new JSONArray(response.body().string());

                food_data.addAll(load_food_data_on_array(array_json));


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return food_data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(List<FoodModel> food_data_result) {
            super.onPostExecute(food_data_result);
            if (food_data_result.size() != 0) {
                g_adapter.notifyDataSetChanged();
                pd.dismiss();
            } else if (food_data_result.size() == 0) {
                pd.dismiss();
                Toast.makeText(getContext(), "No Food Recipes  found " + food_data_result.size() + "", Toast.LENGTH_LONG).show();
            }
            pd.dismiss();
        }

    }
}
