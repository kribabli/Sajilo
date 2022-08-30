package com.sample.sajilo.LoginModule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.R;

public class SignUpActivity extends AppCompatActivity {
    TextView login;
    EditText email;
    EditText password1;
    EditText mobile_no;
    EditText userName;
    Button signUp;
    ImageView facebookLogo;
    ImageView googleLogo;
    ProgressDialog pDialog;
    String email_id,password,moblieNo,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       setInit();
       setAction();
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
        if(NetworkConnection.isConnected(SignUpActivity.this)){
            validation();
        }
        else{
            showToast(getString(R.string.conne_msg));
        }

    }

    private boolean validation() {
        boolean istrue=true;
        email_id=email.getText().toString();
        moblieNo=mobile_no.getText().toString();
        password=password1.getText().toString();
        user=userName.getText().toString();
        try{
            if(email.getText().toString().trim().length()==0){
                email.setError("Please enter email id");
                email.requestFocus();
                istrue=false;

            }
            else if(mobile_no.getText().toString().trim().length()==0||mobile_no.getText().toString().trim().length()<10){
                mobile_no.setError("Please enter your mobile");
                mobile_no.requestFocus();
                istrue=false;
            }
            else if(password1.getText().toString().trim().length()==0||password1.getText().toString().trim().length()<6){
                password1.setError("Please enter password");
                password1.requestFocus();
                istrue=false;

            }
            else if(userName.getText().toString().trim().length()==0){
                userName.setError("Please enter password");
                userName.requestFocus();
                istrue=false;

            }
            else{
                fireToData();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  istrue;
    }

    private void fireToData() {

    }

    public void showToast(String msg) {
        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void dismissProgressDialog() {
        pDialog.dismiss();
    }

    private void setInit() {
        login=findViewById(R.id.login);
        email=findViewById(R.id.email);
        password1=findViewById(R.id.password1);
        mobile_no=findViewById(R.id.mobile_no);
        signUp=findViewById(R.id.signUp);
        facebookLogo=findViewById(R.id.facebookLogo);
        googleLogo=findViewById(R.id.googleLogo);
        userName=findViewById(R.id.userName);
    }
}