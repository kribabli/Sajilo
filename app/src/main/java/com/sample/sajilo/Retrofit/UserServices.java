package com.sample.sajilo.Retrofit;

import android.app.DownloadManager;

import com.sample.sajilo.BottomFragments.Response.ProfileResponse;
import com.sample.sajilo.Model.CategoryReponse;
import com.sample.sajilo.Model.RegisterResponse;
import com.sample.sajilo.ServicesRelatedFragment.Category;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserServices {

    @POST("capi/categorylist.php")
    Call<CategoryReponse> getCategeoryDetails(
            );

    @Multipart
    @POST("userapi/updateprofile.php")
    Call<ProfileResponse>UpdateProfileData(
            @Part("id") RequestBody id,
            @Part("username") RequestBody username,
            @Part("mobile") RequestBody mobile,
            @Part("email") RequestBody email,
            @Part MultipartBody.Part image



            );

}
