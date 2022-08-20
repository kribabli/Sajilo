package com.sample.sajilo.LoginModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.sample.sajilo.MainActivity;
import com.sample.sajilo.R;

public class LoginActvity extends AppCompatActivity {
    EditText email;
    EditText password1;
    ImageView facebookLogo, googleLogo;
    TextView signUp;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_actvity);
        definedfunction();
        setActionfunction();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

    }

    private void setActionfunction() {
        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActvity.this, SignUpActivity.class);
            startActivity(intent);
        });

        googleLogo.setOnClickListener(view -> googleLoginMethod());
    }

    private void googleLoginMethod() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        Intent intent=new Intent(LoginActvity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void definedfunction() {
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);
        facebookLogo = findViewById(R.id.facebookLogo);
        googleLogo = findViewById(R.id.googleLogo);
        signUp = findViewById(R.id.signUp);
    }
}