package com.sample.sajilo.Retrofit;

import com.sample.sajilo.Model.RegisterResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserServices {

    @POST("registeruser")
    Call<RegisterResponse> SendUserDetails(
            @Body JSONObject locationPost
    );

}
