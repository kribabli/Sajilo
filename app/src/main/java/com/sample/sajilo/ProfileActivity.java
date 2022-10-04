package com.sample.sajilo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.util.FileUriUtils;
import com.sample.sajilo.BottomFragments.Response.ProfileResponse;
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.Common.HelperData;
import com.sample.sajilo.Model.CategoryReponse;
import com.sample.sajilo.Retrofit.ApiClient;
import com.sample.sajilo.databinding.ActivityMyProfileBinding;
import com.sample.sajilo.databinding.ActivityProfileBinding;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding activityProfileBinding;
    HelperData helperData;

    String code="",profileImagePath="";
    Bitmap bitmap;
    ProgressDialog progressDialog;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding=ActivityProfileBinding.inflate(getLayoutInflater());
        View view = activityProfileBinding.getRoot();
        setContentView(view);
        helperData=new HelperData(getApplicationContext());
        activityProfileBinding.nameTxt.setText(""+helperData.getUserName());
        activityProfileBinding.emailTxt.setText(""+helperData.getUserEmail());
        activityProfileBinding.mobileTxt.setText(""+helperData.getMobile());
        Log.d("Amit","Value "+helperData.getUserId());
        setAction();
        progressDialog=new ProgressDialog(ProfileActivity.this);
    }

    private void setAction() {
       activityProfileBinding.backgroundImageView.setOnClickListener(v -> {
           progressDialog.show();
           Intent intent=new Intent(ProfileActivity.this,MyProfile.class);
           startActivity(intent);
           finish();
           progressDialog.dismiss();

       });

        activityProfileBinding.updateBtnProfile.setOnClickListener(v -> {
            validationFromBox();


        });


        activityProfileBinding.lytCameraPick.setOnClickListener(v -> {
            code = "profileImage";
            ImagePicker.with(ProfileActivity.this)
                    .crop(16f, 9f)
                    .start();
        });

    }

    private boolean validationFromBox() {
        boolean isValid=true;
        if( activityProfileBinding.nameTxt.getText().toString().trim().length()==0){
            activityProfileBinding.nameTxt.setError("Please enter your username");
            activityProfileBinding.nameTxt.requestFocus();
            isValid=false;

        }
        else if(activityProfileBinding.emailTxt.toString().trim().length()==0){
            activityProfileBinding.emailTxt.setError("Please enter your email");
            activityProfileBinding.emailTxt.requestFocus();
            isValid=false;

        }
        else if(activityProfileBinding.mobileTxt.toString().trim().length()==0){
            activityProfileBinding.mobileTxt.setError("Please enter your mobile");
            activityProfileBinding.mobileTxt.requestFocus();
            isValid=false;
        }
        else if(bitmap==null) {
            Toast.makeText(this, "Please select your Profile Image", Toast.LENGTH_SHORT).show();
           isValid=false;
        }
        else{
//            uploadAllProfileDetails();
            HandleProfileUpdating();
        }

        return  isValid;



    }

    private void HandleProfileUpdating() {
        progressDialog.show();
        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),helperData.getUserId());
        RequestBody requestBody1=RequestBody.create(MediaType.parse("multipart/form-data"),activityProfileBinding.nameTxt.getText().toString());
        RequestBody requestBody2=RequestBody.create(MediaType.parse("multipart/form-data"),activityProfileBinding.mobileTxt.getText().toString());
        RequestBody requestBody3=RequestBody.create(MediaType.parse("multipart/form-data"),activityProfileBinding.emailTxt.getText().toString());
        MultipartBody.Part requestImage=null;
        if(file==null){
            file=new File(profileImagePath);
        }
        okhttp3.RequestBody requestBody4 = okhttp3.RequestBody.create(okhttp3.MediaType.parse("*/*"), file);
        requestImage = MultipartBody.Part.createFormData("profile", file.getName(), requestBody4);
        Log.d("Amit","Value "+helperData.getUserId());

        Call<ProfileResponse> call=ApiClient.getInstance().getApi().UpdateProfileData(requestBody,requestBody1,requestBody2,requestBody3,requestImage);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, retrofit2.Response<ProfileResponse> response) {
                ProfileResponse profileResponse=response.body();
                if(response.isSuccessful()){
                    Log.d("Amit","Value "+profileResponse.getMessage());
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(code.equalsIgnoreCase("profileImage")){
                Uri filePath = data.getData();
                try{
                    profileImagePath = FileUriUtils.INSTANCE.getRealPath(this, data.getData());
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                    helperData.saveProfileImage(filePath);
                    activityProfileBinding.userProfile.setImageURI(filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }
}