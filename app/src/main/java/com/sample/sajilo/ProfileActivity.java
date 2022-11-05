package com.sample.sajilo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.util.FileUriUtils;
import com.sample.sajilo.BottomFragments.Response.ProfileResponse;
import com.sample.sajilo.Common.HelperData;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.Retrofit.ApiClient;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity {
    HelperData helperData;
    String code = "", profileImagePath = "";
    Bitmap bitmap;
    ProgressDialog progressDialog;
    File file;
    EditText name_txt, email_txt, mobile_txt;
    Button update_btn_profile;
    CircleImageView user_profile;
    ImageView lytCameraPick;
    ImageView backPressImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        helperData = new HelperData(getApplicationContext());
        name_txt = findViewById(R.id.name_txt);
        email_txt = findViewById(R.id.email_txt);
        mobile_txt = findViewById(R.id.mobile_txt);
        update_btn_profile = findViewById(R.id.update_btn_profile);
        user_profile = findViewById(R.id.user_profile);
        lytCameraPick = findViewById(R.id.lytCameraPick);
        backPressImage = findViewById(R.id.backPressImage);
        name_txt.setText("" + helperData.getUserName());
        email_txt.setText("" + helperData.getUserEmail());
        mobile_txt.setText("" + helperData.getMobile());
        progressDialog = new ProgressDialog(ProfileActivity.this);
        setAction();
    }

    private void setAction() {

        backPressImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        update_btn_profile.setOnClickListener(v -> {
            validationFromBox();
        });

        lytCameraPick.setOnClickListener(v -> {
            code = "profileImage";
            ImagePicker.with(ProfileActivity.this)
                    .crop(16f, 9f)
                    .start();
        });

    }

    private boolean validationFromBox() {
        boolean isValid = true;
        if (name_txt.getText().toString().trim().length() == 0) {
            name_txt.setError("Please enter your username");
            name_txt.requestFocus();
            isValid = false;

        } else if (email_txt.toString().trim().length() == 0) {
            email_txt.setError("Please enter your email");
            email_txt.requestFocus();
            isValid = false;

        } else if (mobile_txt.toString().trim().length() == 0) {
            mobile_txt.setError("Please enter your mobile");
            mobile_txt.requestFocus();
            isValid = false;
        } else if (bitmap == null) {
            Toast.makeText(this, "Please select your Profile Image", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            if (NetworkConnection.isConnected(this)) {
                HandleProfileUpdating();
            } else {
                Toast.makeText(ProfileActivity.this, "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
            }
        }

        return isValid;


    }

    private void HandleProfileUpdating() {
        progressDialog.show();
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), helperData.getUserId());
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), name_txt.getText().toString());
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), mobile_txt.getText().toString());
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), email_txt.getText().toString());

        MultipartBody.Part fileToUpload1 = null;
        File myFile1 = new File(profileImagePath);

        okhttp3.RequestBody requestBody4 = okhttp3.RequestBody.create(okhttp3.MediaType.parse("*/*"), myFile1);
        fileToUpload1 = MultipartBody.Part.createFormData("image", myFile1.getName(), requestBody4);
        Log.d("Amit", "Value" + fileToUpload1);


        Call<ProfileResponse> call = ApiClient.getInstance().getApi().UpdateProfileData(requestBody, requestBody1, requestBody2, requestBody3, fileToUpload1);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, retrofit2.Response<ProfileResponse> response) {
                Log.d("Amit", "Value " + response);
                ProfileResponse profileResponse = response.body();
                if (response.isSuccessful()) {
                    if (profileResponse.getStatus().equalsIgnoreCase("false")) {
                        Log.d("Amit", "Value6 " + profileResponse.getMessage());
                        Log.d("Amit", "Value7 " + profileResponse.getStatus());
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "" + profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (profileResponse.getStatus().equalsIgnoreCase("true")) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "" + profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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
        if (resultCode == Activity.RESULT_OK) {
            if (code.equalsIgnoreCase("profileImage")) {
                Uri filePath = data.getData();
                try {
                    profileImagePath = FileUriUtils.INSTANCE.getRealPath(this, data.getData());
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                    helperData.saveProfileImage(filePath);
                    user_profile.setImageURI(filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}