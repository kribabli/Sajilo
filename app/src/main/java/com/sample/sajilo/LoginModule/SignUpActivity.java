package com.sample.sajilo.LoginModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    TextView login;
    EditText userName, email, password1, mobile_no;
    Button signUp;
    ImageView facebookLogo;
    ImageView googleLogo;
    String email_id, password, mobileNo, user;
    String url = "http://adminapp.tech/Sajilo/capi/registeruser.php";
    ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progressBar);
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
                SendUserData(user, email_id, password, mobileNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istrue;
    }

    private void SendUserData(String user, String email_id, String password, String mobileNo) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user);
        jsonObject.put("password", password);
        jsonObject.put("email", email_id);
        jsonObject.put("mobile", mobileNo);
        progressBar.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.GONE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(String.valueOf(response.getJSONObject("UserLogin")));
                            if (response.getString("Result").equalsIgnoreCase("false")) {
                                progressBar.setVisibility(View.GONE);
                                signUp.setVisibility(View.VISIBLE);
                                showDialog(response.getString("ResponseMsg"), false);
                                showToast(response.getString("ResponseMsg"));
                            }
                            if (response.getString("Result").equalsIgnoreCase("true")) {
                                progressBar.setVisibility(View.GONE);
                                signUp.setVisibility(View.VISIBLE);
                                showToast(response.getString("ResponseMsg"));
                                Intent intent = new Intent(SignUpActivity.this, LoginActvity.class);
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
                progressBar.setVisibility(View.GONE);
                signUp.setVisibility(View.VISIBLE);
                showToast("Please Register....");

            }
        });
        queue.add(jsonObjectRequest);
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