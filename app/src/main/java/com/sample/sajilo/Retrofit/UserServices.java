package com.sample.sajilo.Retrofit;

import com.sample.sajilo.Model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserServices {

    @FormUrlEncoded
    @POST("registeruser")
    Call<RegisterResponse> SendUserDetails(
            @Field("mobile_no") String mobile_no,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

}
