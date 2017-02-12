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
import com.angolamais.kawakuticode.adapters.GastronomyAdapter;
import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.models.FoodModel;

import java.util.ArrayList;
import java.util.Collections;
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
    private List<FoodModel> recips_list = new ArrayList<>();

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
        requestDataFoodFromWebService(GASTRONOMY_URL_API_LOCAL);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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


        recyclerView.setLayoutManager(linearLayoutManager);


        return view;
    }

    private void requestDataFoodFromWebService(String url_content) {

        MyRecipsAsyncTask task = new MyRecipsAsyncTask();
        task.execute(url_content);
    }

    protected void updateDisplay() {
        if (recips_list != null) {
            g_adapter = new GastronomyAdapter(recips_list, this.getContext());
            recyclerView.setAdapter(g_adapter);
            g_adapter.notifyDataSetChanged();

        } else {
            pd.dismiss();
            Snackbar.make(getView(), "No Food Recipes  found ", Snackbar.LENGTH_LONG).show();
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


    private class MyRecipsAsyncTask extends AsyncTask<String, Void, String> {

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
            recips_list = JsonParsers.foodRecipsParser(stringFromWebService);
            Collections.shuffle(recips_list);
            updateDisplay();
            pd.dismiss();
        }
    }


}
