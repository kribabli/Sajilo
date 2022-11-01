package com.sample.sajilo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sample.sajilo.Adapter.SubCategoryAdapter;
import com.sample.sajilo.BottomFragments.Response.SubCategoryResponse;
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.Common.DatabaseHelper;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.LoginModule.LoginActvity;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.ServicesRelatedFragment.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllSubCategory extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout lyt_not_found;
    ImageView backPress;
    ArrayList<SubCategoryResponse> mListItem;
    DatabaseHelper databaseHelper;
    SubCategoryAdapter adapter;
    String SubCategoryUrl= ConstantClass.Base_Url+"subservice.php";
    String Id="";
    SwipeRefreshLayout swipeRefreshLayout;
    public static String favourite_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sub_category);
        setInit();
        handleButtonAction();
        mListItem = new ArrayList<>();
        databaseHelper=new DatabaseHelper(this);
        if(getIntent().hasExtra("catId")){
            Id=getIntent().getStringExtra("catId");
        }
        Log.d("Amit","Value111 "+databaseHelper.getFavouriteId());
    }

    private void handleButtonAction() {
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setInit() {
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        lyt_not_found=findViewById(R.id.lyt_not_found);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        backPress=findViewById(R.id.backPress);
        Animation animSlideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        recyclerView.startAnimation(animSlideDown);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (NetworkConnection.isConnected(this)) {
            getAllSubCategory();
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    getAllSubCategory();
                    recyclerView.startAnimation(animSlideDown);
                    mListItem.clear();
                    adapter.notifyDataSetChanged();

                }
            });
        } else {
            Toast.makeText(this, "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAllSubCategory() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(AllSubCategory.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, SubCategoryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    if(jsonArray.length()>0){
                        progressBar.setVisibility(View.GONE);
                        mListItem.clear();
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String  id=jsonObject1.getString("id");
                            String  category=jsonObject1.getString("service");
                            String  name=jsonObject1.getString("name");
                            String  cat_img=jsonObject1.getString("image");
                            SubCategoryResponse subCategoryResponse=new SubCategoryResponse(id,category,name,cat_img);
                            mListItem.add(subCategoryResponse);
                        }
                        adapter = new SubCategoryAdapter(mListItem, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        lyt_not_found.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            lyt_not_found.setVisibility(View.VISIBLE);

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sid", Id);
                return params;
            }

        };
        queue.add(stringRequest);





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}