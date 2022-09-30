package com.sample.sajilo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.sample.sajilo.Common.HelperData;
import com.sample.sajilo.databinding.ActivityMyProfileBinding;
import com.sample.sajilo.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding activityProfileBinding;
    HelperData helperData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding=ActivityProfileBinding.inflate(getLayoutInflater());
        View view = activityProfileBinding.getRoot();
        setContentView(view);
        helperData=new HelperData(getApplicationContext());
        activityProfileBinding.nameTxt.setText(""+helperData.getUserName());
        activityProfileBinding.emailTxt.setText(""+helperData.getUserEmail());
        activityProfileBinding.mobileTxt.setText(""+helperData.getMobile());
        setAction();
    }

    private void setAction() {
        activityProfileBinding.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}