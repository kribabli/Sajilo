package com.sample.sajilo.LoginModule;

import android.content.Intent;
import android.os.Bundle;
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
            check_signUp();
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
        email_id = email.getText().toString();
        mobileNo = mobile_no.getText().toString();
        password = password1.getText().toString();
        user = userName.getText().toString();
        try {
            if (email.getText().toString().trim().length() == 0) {
                email.setError("Please enter email id");
                email.requestFocus();
                istrue = false;
            } else if (mobile_no.getText().toString().trim().length() == 0 || mobile_no.getText().toString().trim().length() < 10) {
                mobile_no.setError("Please enter your mobile");
                mobile_no.requestFocus();
                istrue = false;
            } else if (password1.getText().toString().trim().length() == 0 || password1.getText().toString().trim().length() < 6) {
                password1.setError("Please enter password");
                password1.requestFocus();
                istrue = false;
            } else if (userName.getText().toString().trim().length() == 0) {
                userName.setError("Please enter password");
                userName.requestFocus();
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
        Call<RegisterResponse> call = ApiClient.getInstance().getApi().
                SendUserDetails(userName.getText().toString(), email.getText().toString(), password1.getText().toString(), mobile_no.getText().toString());
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful()) {
                    if (registerResponse.getResponseMsg().equalsIgnoreCase("Email Address Already Used!")) {
                        showDialog("" + registerResponse.getResponseCode(), true);
                    }
                    if (registerResponse.getResponseMsg().equalsIgnoreCase("Mobile Number Already Used!")) {
                        showDialog("" + registerResponse.getResult(), true);
                    }
                    if (registerResponse.getResponseMsg().equalsIgnoreCase("Sign Up Done Successfully!")) {
                        showDialog("User Register Successfully..", true);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
            }
        });
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