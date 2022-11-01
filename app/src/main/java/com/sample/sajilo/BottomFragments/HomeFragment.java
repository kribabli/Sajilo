package com.sample.sajilo.BottomFragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.Model.CategoryReponse;
import com.sample.sajilo.Model.Datum;
import com.sample.sajilo.Model.SliderAdapter;
import com.sample.sajilo.R;
import com.sample.sajilo.Retrofit.ApiClient;
import com.sample.sajilo.ServicesRelatedFragment.CategoryAdapter;
import com.sample.sajilo.databinding.FragmentHomeBinding;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding view;
    ArrayList<CategoryDataResponse> mListItem;
    CategoryAdapter adapter;
    ArrayList<Datum> sliderList;
    SliderAdapter sliderAdapter;
    String Banner_url= ConstantClass.Base_Url+"bannerlist.php";
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = FragmentHomeBinding.inflate(inflater, container, false);
        mListItem = new ArrayList<>();
        sliderList=new ArrayList<>();
        view.recyclerView.setHasFixedSize(true);
        Animation animSlideDown = AnimationUtils.loadAnimation(getContext(),R.anim.slide_down);
        view.recyclerView.startAnimation(animSlideDown);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        view.recyclerView.setLayoutManager(layoutManager);
        if (NetworkConnection.isConnected(getActivity())) {
            getCategory();
            getSliderData();
            view.swipeRefreshLayout.setOnRefreshListener(() -> {
                view.swipeRefreshLayout.setRefreshing(false);
                view.recyclerView.startAnimation(animSlideDown);
                getCategory();
                mListItem.clear();
                adapter.notifyDataSetChanged();
            });
        } else {
            Toast.makeText(getActivity(), "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
        }
        return view.getRoot();

    }

    private void displaySliderImage() {
        if (!sliderList.isEmpty()) {
            sliderAdapter = new SliderAdapter(requireActivity(), sliderList);
            view.viewPager.setAdapter(sliderAdapter);
            final Handler handler = new Handler();
            final Runnable Update = () -> {
                if (currentPage == sliderList.size()) {
                    currentPage = 0;
                }
                view.viewPager.setCurrentItem(currentPage++, true);
            };
            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);
        } else {
            view.lytSlider.setVisibility(View.GONE);
        }

    }

    private void getSliderData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Banner_url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("data");
                    Log.d("Amit","Slider List"+jsonArray);
                    if(response.getString("Result").equalsIgnoreCase("true")){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            String id=jsonObject.getString("id");
                            String img=jsonObject.getString("img");
                            String status=jsonObject.getString("status");
                            String is_main=jsonObject.getString("is_main");
                            Datum datum=new Datum(id,img,status,is_main);
                            sliderList.add(datum);
                           }
                        displaySliderImage();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> {

        });
        queue.add(jsonObjectRequest);




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
                                    String cat_img = jsonObject.getString("image");
                                    String cat_name = jsonObject.getString("name");
                                    String cat_status = jsonObject.getString("status");
                                    String id = jsonObject.getString("id");
                                    CategoryDataResponse categoryReponse1 = new CategoryDataResponse(id, cat_name, cat_status, cat_img,"");
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