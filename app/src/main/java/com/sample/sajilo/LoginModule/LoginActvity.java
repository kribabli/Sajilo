package com.sample.sajilo.LoginModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.sajilo.R;

public class LoginActvity extends AppCompatActivity {
    EditText email;
    EditText password1;
    ImageView facebookLogo,googleLogo;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_actvity);
       definedfunction();
       setActionfunction();
    }

    private void setActionfunction() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActvity.this,SignUpActivity.class);
                startActivity(intent);

            }
        });

    }

    private void definedfunction() {
        email=findViewById(R.id.email);
        password1=findViewById(R.id.password1);
        facebookLogo=findViewById(R.id.facebookLogo);
        googleLogo=findViewById(R.id.googleLogo);
        signUp=findViewById(R.id.signUp);


    }
}