package com.sample.sajilo.BottomFragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sample.sajilo.Grocery.GroceryAdapter.AllCategoryAdapter;
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.R;
import com.sample.sajilo.databinding.FragmentShopNowBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShopNowFragment extends Fragment {
    String Category_url= ConstantClass.Base_Url+"categorylist.php";
    ArrayList<CategoryDataResponse> mListItem;
    AllCategoryAdapter adapter;
    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update
    private @NonNull FragmentShopNowBinding view;
    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE=101;
    String pinCode="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view=FragmentShopNowBinding.inflate(inflater,container,false);
        mListItem = new ArrayList<>();
        view.recyclerView.setHasFixedSize(true);
        Animation animSlideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        view.recyclerView.startAnimation(animSlideDown);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        view.recyclerView.setLayoutManager(layoutManager);
        if (NetworkConnection.isConnected(getActivity())) {
            fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getContext());
            getLastLocation();
            getAllCategory();
            getSearchData();
            view.swipeRefreshLayout.setOnRefreshListener(() -> {
                view.swipeRefreshLayout.setRefreshing(false);
                view.recyclerView.startAnimation(animSlideDown);
                getLastLocation();
                getAllCategory();
                mListItem.clear();
                adapter.notifyDataSetChanged();
            });
        } else {
            Toast.makeText(getActivity(), "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
        }

        return view.getRoot();
    }

    private void getSearchData() {
        view.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

    }

    private void filterList(String text) {
        List<CategoryDataResponse> filteredList= new ArrayList<>();
        for(CategoryDataResponse categoryDataResponse: mListItem){
            if(categoryDataResponse.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(categoryDataResponse);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        }
        else{
          adapter.setFilterList(filteredList);
        }

    }

    private void getLastLocation() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        Geocoder geocoder=new Geocoder(getContext(), Locale.getDefault());
                        try {
                            Log.d("Amit","Value A "+location);
                            List<Address> addressList=geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            pinCode=addressList.get(0).getPostalCode();
                            Log.d("Amit","Value B "+pinCode);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                }
            });



        }

    }

    private void getAllCategory() {
        view.progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Category_url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("Result").equalsIgnoreCase("true")){
                        JSONArray jsonArray=response.getJSONArray("data");
                        if(jsonArray.length()>0){
                            view.progressBar.setVisibility(View.GONE);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String id=jsonObject.getString("id");
                                String cat_name=jsonObject.getString("cat_name");
                                String cat_status=jsonObject.getString("cat_status");
                                String cat_img=jsonObject.getString("cat_img");
                                Log.d("Amit","Value C "+pinCode);
                                CategoryDataResponse categoryReponse1 = new CategoryDataResponse(id, cat_name, cat_status, cat_img,pinCode);
                                mListItem.add(categoryReponse1);
                            }
                            adapter = new AllCategoryAdapter(mListItem, getContext());
                            view.recyclerView.setAdapter(adapter);
                        }
                        else{
                            view.progressBar.setVisibility(View.GONE);
                        }


                    }
                    else {
                        view.progressBar.setVisibility(View.GONE);
                    }




                } catch (JSONException e) {
                    view.progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
        }, error -> {
        });
        queue.add(jsonObjectRequest);

    }
}