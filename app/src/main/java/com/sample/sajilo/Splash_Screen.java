package com.sample.sajilo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.sajilo.LoginModule.LoginActvity;

public class Splash_Screen extends AppCompatActivity {
    ImageView logoImageView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        logoImageView = (ImageView) findViewById(R.id.logoImageView);
//        Animation slideAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.side_slide);
//        logoImageView.startAnimation(slideAnimation);
        animatedfunction();
    }

    private void animatedfunction() {
        handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Splash_Screen.this, LoginActvity.class);
            startActivity(intent);
            finish();

        }, 3000);
    }
}