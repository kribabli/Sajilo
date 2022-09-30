package com.sample.sajilo.LoginModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.MyProfile;
import com.sample.sajilo.databinding.ActivityChangePasswordBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    String url = ConstantClass.Base_Url +"updatepassword.php";
    String email, mobile, password;
    ActivityChangePasswordBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityChangePasswordBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        setAction();

    }

    private void setAction() {
        if(getIntent().hasExtra("email")){
            activityMainBinding.yourMobileNo.setText(""+getIntent().getStringExtra("email"));
        }
        activityMainBinding.update.setOnClickListener(view -> {
            try {
                validation();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

    private boolean validation() throws JSONException {
        boolean isTrue=true;
        if(activityMainBinding.yourMobileNo.getText().toString().trim().length()==0){
            activityMainBinding.yourMobileNo.setError("Please enter mobile/email");
            activityMainBinding.yourMobileNo.requestFocus();
            isTrue=false;
        }
        else if(activityMainBinding.newPassword.getText().toString().trim().length()==0){
            activityMainBinding.newPassword.setError("Please enter your new password");
            activityMainBinding.newPassword.requestFocus();
            isTrue=false;
        }
        else if(activityMainBinding.newPassword.getText().toString().trim().length()<6){
            activityMainBinding.newPassword.setError("Enter 6 digit above password");
            activityMainBinding.newPassword.requestFocus();
            isTrue=false;
        }
        else{
            email=activityMainBinding.yourMobileNo.getText().toString();
            password=activityMainBinding.newPassword.getText().toString();
            getUserData(email,password);
        }
        return isTrue;
    }

    private void getUserData(String email,  String password) throws JSONException {
        activityMainBinding.update.setVisibility(View.GONE);
        activityMainBinding.progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject=new JSONObject(String.valueOf(response));
                            if(jsonObject.getString("Result").equalsIgnoreCase("false")){
                                activityMainBinding.progressBar.setVisibility(View.GONE);
                                activityMainBinding.update.setVisibility(View.VISIBLE);
                                Toast.makeText(ChangePasswordActivity.this, ""+jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            }
                            else if(jsonObject.getString("Result").equalsIgnoreCase("true")){
                                Toast.makeText(ChangePasswordActivity.this, ""+jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ChangePasswordActivity.this,LoginActvity.class);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activityMainBinding.progressBar.setVisibility(View.GONE);
                activityMainBinding.update.setVisibility(View.VISIBLE);
                Toast.makeText(ChangePasswordActivity.this, "Somethings went wrong..", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonObjectRequest);

    }

}