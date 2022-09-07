package com.sample.sajilo.Retrofit;

import com.sample.sajilo.Model.CategoryReponse;
import com.sample.sajilo.Model.RegisterResponse;
import com.sample.sajilo.ServicesRelatedFragment.Category;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserServices {

    @POST("categorylist.php")
    Call<CategoryReponse> getCategeoryDetails(
            );

}
