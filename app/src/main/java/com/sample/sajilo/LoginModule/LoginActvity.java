package com.sample.sajilo.LoginModule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.sample.sajilo.Common.HelperData;
import com.sample.sajilo.MainActivity;
import com.sample.sajilo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActvity extends AppCompatActivity {
    EditText email;
    EditText password1;
    ImageView facebookLogo, googleLogo;
    TextView signUp;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button login;
    String email_id, password;
    ProgressBar progressBar;
    CheckBox checkBox;
    HelperData helperData;

    String url = "http://adminapp.tech/Sajilo/capi/userlogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_actvity);
        initMethod();
        setAction();
        helperData=new HelperData(getApplicationContext());
    }

    private void initMethod() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);
        facebookLogo = findViewById(R.id.facebookLogo);
        googleLogo = findViewById(R.id.googleLogo);
        signUp = findViewById(R.id.signUp);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        checkBox = findViewById(R.id.checkbox_login_activity);
    }

    private void setAction() {
        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActvity.this, SignUpActivity.class);
            startActivity(intent);
        });

        googleLogo.setOnClickListener(view -> signIn());

        facebookLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });

        login.setOnClickListener(view -> {
            validation();


        });
    }

    private boolean validation() {
        boolean istrue = true;
        email_id = email.getText().toString();
        password = password1.getText().toString();
        try {
            if (email.getText().toString().trim().length() == 0) {
                email.setError("Please enter email id");
                email.requestFocus();
                istrue = false;
            } else if (password1.getText().toString().trim().length() == 0 || password1.getText().toString().trim().length() < 6) {
                password1.setError("Please enter password");
                password1.requestFocus();
                istrue = false;
            }
            else{
                checkBox.setChecked(true);
                if(checkBox.isChecked()){
                    helperData.saveIsRemember(true);
                    helperData.saveRemember(email_id,password);
                }
                validationfromserver(email_id,password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istrue;

    }

    private void validationfromserver(String email_id, String password) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(LoginActvity.this);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("password",password);
        jsonObject.put("email",email_id);
        progressBar.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            if(response.getString("Result").equalsIgnoreCase("false")){
                                progressBar.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActvity.this, ""+response.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            }
                            if(response.getString("Result").equalsIgnoreCase("true")){
                                JSONObject jsonObject1=new JSONObject(String.valueOf(response.getJSONObject("UserLogin")));
                                progressBar.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                helperData.saveIsLogin(true);
                                helperData.saveLogin(jsonObject1.getString("id"),jsonObject1.getString("username"),jsonObject1.getString("email"));
                                Toast.makeText(LoginActvity.this, ""+response.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActvity.this,MainActivity.class);
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
                login.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActvity.this, "Somethings went wrong..", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonObjectRequest);

    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                loginMethod();
            } catch (ApiException e) {
                Toast.makeText(this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loginMethod() {
        Intent intent = new Intent(LoginActvity.this, MainActivity.class);
        intent.putExtra("checkData", true);
        startActivity(intent);
        finish();
    }

    private void facebookLogin() {

    }
}