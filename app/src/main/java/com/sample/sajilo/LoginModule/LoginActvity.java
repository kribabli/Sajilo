package com.sample.sajilo.LoginModule;

import android.content.Intent;
import android.net.Uri;
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
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.Common.HelperData;
import com.sample.sajilo.MainActivity;
import com.sample.sajilo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class LoginActvity extends AppCompatActivity {
    EditText email;
    EditText password1;
    ImageView  googleLogo;
    TextView signUp, forgotPassword;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button login;
    String email_id, password,UserEmail,UserName;
    ProgressBar progressBar;
    CheckBox checkBox;
    HelperData helperData;
    String url = ConstantClass.Base_Url+"userlogin.php";
    String google_login_url = ConstantClass.Base_Url+"googlelogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_actvity);
        initMethod();
        setAction();
        helperData = new HelperData(getApplicationContext());
    }

    private void initMethod() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);

        googleLogo = findViewById(R.id.googleLogo);
        signUp = findViewById(R.id.signUp);
        forgotPassword = findViewById(R.id.forgotPassword);
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

        login.setOnClickListener(view -> {
            validation();
        });

        forgotPassword.setOnClickListener(view -> validationOnlyEmail());
    }

    private boolean validationOnlyEmail() {
        boolean istrue = true;
        try{
            if(email.getText().toString().trim().length()==0){
                email.setError("Please enter email id");
                email.requestFocus();
                istrue = false;
            }
            else{
                String email_id=email.getText().toString();
                chnagePasswordRequest(email_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istrue;

    }

    private void chnagePasswordRequest(String email_id) {
        Intent intent=new Intent(LoginActvity.this,ChangePasswordActivity.class);
        intent.putExtra("email",email_id);
        startActivity(intent);
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
            } else {
                checkBox.setChecked(true);
                if (checkBox.isChecked()) {
                    helperData.saveIsRemember(true);
                    helperData.saveRemember(email_id, password);
                }
                validationfromserver(email_id, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istrue;
    }

    private void validationfromserver(String email_id, String password) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(LoginActvity.this);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password", password);
        jsonObject.put("email", email_id);
        progressBar.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    try {
                        progressBar.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        if (response.getString("Result").equalsIgnoreCase("false")) {
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActvity.this, "" + response.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                        }
                        if (response.getString("Result").equalsIgnoreCase("true")) {
                            JSONObject jsonObject1 = new JSONObject(String.valueOf(response.getJSONObject("UserLogin")));
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            helperData.saveIsLogin(true);
                            Log.d("Amit","Value  "+jsonObject1);
                            String refer_code;
                            if(jsonObject1.isNull("refferal_code")){
                                refer_code="Sajilo123";
                            }
                            else{
                                refer_code=jsonObject1.getString("refferal_code");
                            }
                            if(jsonObject1.isNull("image")){

                            }
                            else{
                                Log.d("Amit","Value check 111 "+jsonObject1.getString("image"));
                                helperData.saveProfileImage(Uri.parse(jsonObject1.getString("image")));
                            }

                            helperData.saveLogin(jsonObject1.getString("id"), jsonObject1.getString("username"), jsonObject1.getString("email"),jsonObject1.getString("mobile"),refer_code);
                            Toast.makeText(LoginActvity.this, "" + response.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActvity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    progressBar.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActvity.this, "Somethings went wrong..", Toast.LENGTH_SHORT).show();
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loginMethod() throws JSONException {
        login.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            UserName = googleSignInAccount.getDisplayName();
            UserEmail = googleSignInAccount.getEmail();
            Uri photoUrl = googleSignInAccount.getPhotoUrl();
            String id = googleSignInAccount.getId();
        }
        RequestQueue queue = Volley.newRequestQueue(LoginActvity.this);
        JSONObject jsonObject=new JSONObject();
        Log.d("Amit","Value "+UserEmail);
        jsonObject.put("email",UserEmail);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, google_login_url, jsonObject,
                response -> {
                    try {
                        Log.d("Amit","Value "+response.toString());
                        if(response.getString("Result").equalsIgnoreCase("false")){
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActvity.this, ""+response.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            gsc.signOut();
                            Intent intent=new Intent(LoginActvity.this,SignUpActivity.class);
                            intent.putExtra("email",UserEmail);
                            startActivity(intent);
                            finish();
                        }
                        else if(response.getString("Result").equalsIgnoreCase("true")){
                            login.setVisibility(View.VISIBLE);
                            JSONObject jsonObject1=new JSONObject(String.valueOf(response.getJSONObject("userlogin")));
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActvity.this, ""+response.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            helperData.saveIsLogin(true);
                            String refer_code;
                            if(jsonObject1.isNull("refferal_code")){
                                refer_code="Sajilo123";
                            }
                            else{
                                refer_code=jsonObject1.getString("refferal_code");

                            }
                            helperData.saveLogin(jsonObject1.getString("id"),jsonObject1.getString("username"),jsonObject1.getString("email"),jsonObject1.getString("mobile"),refer_code);
                            Intent intent=new Intent(LoginActvity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActvity.this, ""+response.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    Log.d("Amit","Value "+error);
                    progressBar.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActvity.this, "Somethings went wrong...", Toast.LENGTH_SHORT).show();

                });
        queue.add(jsonObjectRequest);
    }


}