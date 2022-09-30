package com.sample.sajilo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.util.FileUriUtils;
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.Common.HelperData;
import com.sample.sajilo.LoginModule.ChangePasswordActivity;
import com.sample.sajilo.LoginModule.LoginActvity;
import com.sample.sajilo.databinding.ActivityMyProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {
    ActivityMyProfileBinding activityMyProfileBinding;
    String code="",profileImagePath;
    HelperData helperData;
    AlertDialog deleteDialog;
    String Change_password_url= ConstantClass.Base_Url+"updatepassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyProfileBinding=ActivityMyProfileBinding.inflate(getLayoutInflater());
        View view = activityMyProfileBinding.getRoot();
        setContentView(view);
        setAction();
        helperData=new HelperData(getApplicationContext());
        activityMyProfileBinding.userName.setText(""+helperData.getUserName());
        activityMyProfileBinding.userEmail.setText(""+helperData.getUserEmail());

        if(helperData.getUserProfileImage()!=null){
            activityMyProfileBinding.profilePic.setImageURI(helperData.getUserProfileImage());
        }


    }

    private void setAction() {
        activityMyProfileBinding.profileChange.setOnClickListener(view -> {
            code = "profileImage";
            ImagePicker.with(MyProfile.this)
                    .crop(16f, 9f)
                    .start();

        });

        activityMyProfileBinding.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dialogView= LayoutInflater.from(MyProfile.this).inflate(R.layout.customised_dialog_layout, null);

                EditText nameEdt=dialogView.findViewById(R.id.nameEdt);
                EditText passwordEdt=dialogView.findViewById(R.id.passwordEdt);
                Button updateBtn=dialogView.findViewById(R.id.updateBtn);
                ImageView close_img=dialogView.findViewById(R.id.close_img);
                nameEdt.setText(""+helperData.getUserEmail());
                updateBtn.setOnClickListener(view1 -> {
                   if(nameEdt.getText().toString().trim().length()==0){
                       nameEdt.setError("Please enter your email");
                       nameEdt.requestFocus();
                   }
                   else if(passwordEdt.getText().toString().trim().length()==0|| passwordEdt.getText().toString().trim().length()<6){
                       passwordEdt.setError("password length 6 or more");
                   }
                   else {
                       try {
                           HandleUpdatePassword(helperData.getUserEmail().toString(),passwordEdt.getText().toString());
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }


                });

                close_img.setOnClickListener(view12 -> deleteDialog.dismiss());


                deleteDialog = new AlertDialog.Builder(MyProfile.this).create();
                deleteDialog.setView(dialogView);
                deleteDialog.show();
                deleteDialog.getWindow().setGravity(Gravity.BOTTOM);

            }
        });

        activityMyProfileBinding.linearlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyProfile.this,ProfileActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void HandleUpdatePassword(String email,  String password) throws JSONException {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();


        RequestQueue queue = Volley.newRequestQueue(MyProfile.this);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Change_password_url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.show();
                            if (response.getString("Result").equalsIgnoreCase("false")) {
                                progressDialog.dismiss();
                                Toast.makeText(MyProfile.this, "" + response.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            }
                            if (response.getString("Result").equalsIgnoreCase("true")) {
                                progressDialog.dismiss();
                                Toast.makeText(MyProfile.this, "" + response.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MyProfile.this, "Somethings went wrong..", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
           if(code.equalsIgnoreCase("profileImage")) {
               profileImagePath = FileUriUtils.INSTANCE.getRealPath(this, data.getData());
               Uri selectedImage = data.getData();
               activityMyProfileBinding.profilePic.setImageURI(selectedImage);
               helperData.saveProfileImage(selectedImage);
           }

        }
    }
}