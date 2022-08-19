package com.sample.sajilo.LoginModule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.sajilo.R;

public class SignUpActivity extends AppCompatActivity {
    TextView login;
    EditText email;
    EditText password1;
    EditText password2;
    Button signUp;
    ImageView facebookLogo;
    ImageView googleLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       setInit();
    }

    private void setInit() {
        login=findViewById(R.id.login);
        email=findViewById(R.id.email);
        password1=findViewById(R.id.password1);
        password2=findViewById(R.id.password2);
        signUp=findViewById(R.id.signUp);
        facebookLogo=findViewById(R.id.facebookLogo);
        googleLogo=findViewById(R.id.googleLogo);
    }
}