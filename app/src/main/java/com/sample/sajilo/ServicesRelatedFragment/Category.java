package com.sample.sajilo.ServicesRelatedFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.Model.CategoryReponse;
import com.sample.sajilo.R;
import com.sample.sajilo.Retrofit.ApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Category extends Fragment {

    ArrayList<CategoryDataResponse> mListItem;
    public RecyclerView recyclerView;
    CategoryAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout lyt_not_found;
    boolean isFirst = true, isOver = false;
    private int pageIndex = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_category, container, false);

        mListItem = new ArrayList<>();
        lyt_not_found = rootView.findViewById(R.id.lyt_not_found);
        progressBar = rootView.findViewById(R.id.progressBar);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        if (NetworkConnection.isConnected(getActivity())) {
            getCategory();
        } else {
            Toast.makeText(getActivity(), "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    private void getCategory() {
        showProgress(true);

        Call<CategoryReponse> call= ApiClient.getInstance().getApi().getCategeoryDetails();

        call.enqueue(new Callback<CategoryReponse>() {
            @Override
            public void onResponse(Call<CategoryReponse> call, Response<CategoryReponse> response) {
                CategoryReponse categoryReponse=response.body();
                if(response.isSuccessful()){
                    if(categoryReponse.getResult().equalsIgnoreCase("true")){
                        String categoryData = new Gson().toJson(categoryReponse.getData());
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(categoryData);
                            if (jsonArray.length() > 0) {
                                showProgress(false);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String cat_img = jsonObject.getString("cat_img");
                                String cat_name = jsonObject.getString("cat_name");
                                String cat_status = jsonObject.getString("cat_status");
                                String id = jsonObject.getString("id");
                                CategoryDataResponse categoryReponse1 = new CategoryDataResponse(id, cat_name, cat_status, cat_img);
                                mListItem.add(categoryReponse1);

                            }
                                adapter=new CategoryAdapter(mListItem,getContext());
                                recyclerView.setAdapter(adapter);
                        } else{
                                lyt_not_found.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if(categoryReponse.getResult().equalsIgnoreCase("false")){
                        showProgress(false);
                        lyt_not_found.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onFailure(Call<CategoryReponse> call, Throwable t) {
                showProgress(false);
                lyt_not_found.setVisibility(View.VISIBLE);

            }
        });


    }

    private void showProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            lyt_not_found.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}