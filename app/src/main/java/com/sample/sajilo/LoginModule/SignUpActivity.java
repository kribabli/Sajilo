package com.sample.sajilo.LoginModule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.Model.RegisterResponse;
import com.sample.sajilo.R;
import com.sample.sajilo.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    TextView login;
    EditText userName, email, password1, mobile_no;
    Button signUp;
    ImageView facebookLogo;
    ImageView googleLogo;
    String email_id, password, mobileNo, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initMethod();
        setAction();
    }

    private void initMethod() {
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);
        mobile_no = findViewById(R.id.mobile_no);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
        facebookLogo = findViewById(R.id.facebookLogo);
        googleLogo = findViewById(R.id.googleLogo);
    }

    private void setAction() {
        login.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActvity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        signUp.setOnClickListener(view -> {
//            check_signUp();
            validation();
        });
    }

    private void check_signUp() {
        if (NetworkConnection.isConnected(SignUpActivity.this)) {
            validation();
        } else {
            showToast(getString(R.string.connection_msg));
        }
    }

    private boolean validation() {
        boolean istrue = true;
        user = userName.getText().toString();
        email_id = email.getText().toString();
        password = password1.getText().toString();
        mobileNo = mobile_no.getText().toString();
        try {
            if (userName.getText().toString().trim().length() == 0) {
                userName.setError("Please enter name");
                userName.requestFocus();
                istrue = false;
            } else if (email.getText().toString().trim().length() == 0) {
                email.setError("Please enter email id");
                email.requestFocus();
                istrue = false;
            } else if (password1.getText().toString().trim().length() == 0 || password1.getText().toString().trim().length() < 6) {
                password1.setError("Please enter password");
                password1.requestFocus();
                istrue = false;
            } else if (mobile_no.getText().toString().trim().length() == 0 || mobile_no.getText().toString().trim().length() < 10) {
                mobile_no.setError("Please enter your mobile");
                mobile_no.requestFocus();
                istrue = false;
            } else {
                SendUserData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istrue;
    }

    private void SendUserData() {
        Log.d("TAG", "SendUserData1: ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", userName.getText().toString());
            jsonObject.put("email", email.getText().toString());
            jsonObject.put("mobile", mobile_no.getText().toString());
            jsonObject.put("password", password1.getText().toString());
            Call<RegisterResponse> call = ApiClient.getInstance().getApi().
                    SendUserDetails(jsonObject);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    RegisterResponse registerResponse = response.body();
                    Log.d("TAG", "onResponse22: " + registerResponse);
                    if (response.isSuccessful()) {
                        Log.d("TAG", "SendUserData12: ");
                        if (registerResponse.getResponseMsg().equalsIgnoreCase("Email Address Already Used!")) {

                            Log.d("TAG", "SendUserData13: ");
                            showDialog("" + registerResponse.getResult(), true);
                        }
                        if (registerResponse.getResponseMsg().equalsIgnoreCase("Mobile Number Already Used!")) {

                            Log.d("TAG", "SendUserData14: ");
                            showDialog("" + registerResponse.getResult(), true);
                        }
                        if (registerResponse.getResponseMsg().equalsIgnoreCase("Sign Up Done Successfully!")) {

                            Log.d("TAG", "SendUserData15: ");
                            showDialog("User Register Successfully..", true);
                        }
                    } else {
                        Log.d("TAG", "onResponse: " + response.body());
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Log.d("TAG", "onFailure: " + t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showDialog(String message, Boolean isFinish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (dialog, id) -> {
            dialog.dismiss();
            if (isFinish) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}