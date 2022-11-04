package com.sample.sajilo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sample.sajilo.Adapter.OnlyImageAdapter;
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.Model.CategoryReponse;
import com.sample.sajilo.Retrofit.ApiClient;
import com.sample.sajilo.ServicesRelatedFragment.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_information_Services extends AppCompatActivity {
    String Url= ConstantClass.Base_Url+"serviceproduct.php";
    ProgressBar progress_circular;
    TextView title_tv,description_tv,address_tv;
    ImageView backPressImage;
    RecyclerView recyclerViewImage;
    OnlyImageAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_information_services);
        handleInitialSetValue();

    }

    private void handleInitialSetValue() {
        progress_circular=findViewById(R.id.progress_circular);
        title_tv=findViewById(R.id.title_tv);
        description_tv=findViewById(R.id.description_tv);
        address_tv=findViewById(R.id.address_tv);
        backPressImage=findViewById(R.id.backPressImage);
        recyclerViewImage=findViewById(R.id.recyclerViewImage);
        Animation animSlideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        recyclerViewImage.startAnimation(animSlideDown);
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(this,2));
        if(NetworkConnection.isConnected(this)){
            if(getIntent().hasExtra("service") && getIntent().hasExtra("subservice")){
                try {
                    CallApiForData(getIntent().getStringExtra("service"),getIntent().getStringExtra("subservice"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            Toast.makeText(this, "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
        }
    }

    private void CallApiForData(String service, String subservice) throws JSONException {
        progress_circular.setVisibility(View.VISIBLE);

       /* RequestQueue queue = Volley.newRequestQueue(All_information_Services.this);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("service",service);
        jsonObject.put("subservice",subservice);

        Log.d("Amit","Value  "+jsonObject);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=new JSONArray(response.getJSONArray("StoreData"));
                    Log.d("Amit","Value check 11 "+response);
                    progress_circular.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);*/


        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("service", service);
        requestBody.put("subservice", subservice);

        Call<Object> call= ApiClient.getInstance().getApi().getAllInformation(requestBody);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.isSuccessful()){
                    JSONObject object= null;
                    try {
                        object = new JSONObject(new Gson().toJson(response.body()));
                        Log.e("Amit", "onResponse: "+object);
                        JSONArray jsonArray=object.getJSONArray("StoreData");
                        Log.e("Amit", "onResponse1: "+jsonArray);
                        if(jsonArray.length()>0){
                            progress_circular.setVisibility(View.GONE);
                            for(int i=0; i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                title_tv.setText(jsonObject.getString("title"));
                                description_tv.setText(jsonObject.getString("description"));

                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("TAG", "onResponse: "+e.toString());
                    }


                }


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });



    }
}