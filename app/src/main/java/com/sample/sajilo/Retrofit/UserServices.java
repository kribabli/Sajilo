package com.sample.sajilo.Retrofit;


import com.sample.sajilo.BottomFragments.Response.ProfileResponse;
import com.sample.sajilo.Model.CategoryReponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserServices {

    @POST("capi/serviceslist.php")
    Call<CategoryReponse> getCategeoryDetails(
            );

    @Multipart
    @POST("userapi/updateprofile.php")
    Call<ProfileResponse>UpdateProfileData(
            @Part("id") okhttp3.RequestBody id,
            @Part("username") okhttp3.RequestBody username,
            @Part("mobile") okhttp3.RequestBody mobile,
            @Part("email") RequestBody email,
            @Part MultipartBody.Part image
    );


    @POST("capi/serviceproduct.php")
    Call<Object> getAllInformation(@Body Map<String, String> body);






}
