package com.sample.sajilo.Grocery.GroceryAllActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sample.sajilo.Grocery.GroceryAdapter.All_vendors_list_Adapter;
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.Model.VendorsStoreData;
import com.sample.sajilo.R;
import com.sample.sajilo.databinding.ActivityAllVendorsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllVendors extends AppCompatActivity {
   private ActivityAllVendorsBinding activityAllVendorsBinding;
    String category="",pinCode="";
    String url= ConstantClass.Base_Url+"p_store_list.php";
    All_vendors_list_Adapter adapter;
    ArrayList<VendorsStoreData> mListItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAllVendorsBinding=ActivityAllVendorsBinding.inflate(getLayoutInflater());
        View view = activityAllVendorsBinding.getRoot();
        setContentView(view);
        try {
            setInitAction();
            setAction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAction() {
        activityAllVendorsBinding.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setInitAction() throws JSONException {
        Animation animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        activityAllVendorsBinding.recyclerView.startAnimation(animSlideDown);
        activityAllVendorsBinding.recyclerView.setHasFixedSize(true);
        activityAllVendorsBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListItem = new ArrayList<>();
        if (NetworkConnection.isConnected(this)) {
            if(getIntent().hasExtra("category")&& getIntent().hasExtra("pincode")){
                category=getIntent().getStringExtra("category");
                pinCode=getIntent().getStringExtra("pincode");
                Log.d("Amit","Value check 111111 "+pinCode);

            }
            getAllVendorsData(category,pinCode);
            activityAllVendorsBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    activityAllVendorsBinding.swipeRefreshLayout.setRefreshing(false);
                    try {
                        getAllVendorsData(category,pinCode);
                        mListItem.clear();
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    activityAllVendorsBinding.recyclerView.startAnimation(animSlideDown);
//                    mListItem.clear();
//                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            Toast.makeText(this, "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
        }

    }





    private void getAllVendorsData(String category, String pinCode) throws JSONException {

        activityAllVendorsBinding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(AllVendors.this);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pincode",pinCode);
        jsonObject.put("category",category);

        Log.d("Amit"," "+url   +" " +jsonObject);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Amit","Value 11 "+response);
                try {
                    if(response.getString("Result").equalsIgnoreCase("true")){
                        activityAllVendorsBinding.progressBar.setVisibility(View.GONE);
                        JSONArray jsonArray=response.getJSONArray("StoreData");
                        if(jsonArray.length()>0){
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String id=jsonObject1.getString("id");
                                String title=jsonObject1.getString("title");
                                String store_image=jsonObject1.getString("store_image");
                                String IS_OPEN=jsonObject1.getString("IS_OPEN");
                                String address=jsonObject1.getString("address");
                                String mobile=jsonObject1.getString("mobile");
                                String star=jsonObject1.getString("star");
                                String Total_Items=jsonObject1.getString("Total_Items");
                                VendorsStoreData vendorsStoreData=new VendorsStoreData(id,title,store_image,IS_OPEN,address,mobile,star,Total_Items);
                                mListItem.add(vendorsStoreData);
                            }
                            adapter = new All_vendors_list_Adapter(mListItem, AllVendors.this);
                            activityAllVendorsBinding.recyclerView.setAdapter(adapter);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    activityAllVendorsBinding.progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Amit","yaha  "+error);
                activityAllVendorsBinding.progressBar.setVisibility(View.GONE);

            }
        });
        queue.add(jsonObjectRequest);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}