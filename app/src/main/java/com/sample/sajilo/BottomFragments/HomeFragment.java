package com.sample.sajilo.BottomFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.Model.CategoryReponse;
import com.sample.sajilo.R;
import com.sample.sajilo.Retrofit.ApiClient;
import com.sample.sajilo.ServicesRelatedFragment.CategoryAdapter;
import com.sample.sajilo.databinding.ActivityChangePasswordBinding;
import com.sample.sajilo.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding view;
    ArrayList<CategoryDataResponse> mListItem;
    CategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = FragmentHomeBinding.inflate(inflater, container, false);
        mListItem = new ArrayList<>();
        view.recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        view.recyclerView.setLayoutManager(layoutManager);
        if (NetworkConnection.isConnected(getActivity())) {
            getCategory();
        } else {
            Toast.makeText(getActivity(), "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
        }
        return view.getRoot();
    }

    private void getCategory() {
        view.progressBar.setVisibility(View.VISIBLE);
        Call<CategoryReponse> call = ApiClient.getInstance().getApi().getCategeoryDetails();
        call.enqueue(new Callback<CategoryReponse>() {
            @Override
            public void onResponse(Call<CategoryReponse> call, Response<CategoryReponse> response) {
                CategoryReponse categoryReponse = response.body();
                if (response.isSuccessful()) {
                    if (categoryReponse.getResult().equalsIgnoreCase("true")) {
                        String categoryData = new Gson().toJson(categoryReponse.getData());
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(categoryData);
                            if (jsonArray.length() > 0) {
                                view.progressBar.setVisibility(View.GONE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String cat_img = jsonObject.getString("cat_img");
                                    String cat_name = jsonObject.getString("cat_name");
                                    String cat_status = jsonObject.getString("cat_status");
                                    String id = jsonObject.getString("id");
                                    CategoryDataResponse categoryReponse1 = new CategoryDataResponse(id, cat_name, cat_status, cat_img);
                                    mListItem.add(categoryReponse1);
                                }
                                adapter = new CategoryAdapter(mListItem, getContext());
                                view.recyclerView.setAdapter(adapter);
                            } else {
                                view.lytNotFound.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (categoryReponse.getResult().equalsIgnoreCase("false")) {
                        view.progressBar.setVisibility(View.GONE);
                        view.lytNotFound.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryReponse> call, Throwable t) {
                view.progressBar.setVisibility(View.GONE);
                view.lytNotFound.setVisibility(View.VISIBLE);
            }
        });
    }
}