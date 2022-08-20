package com.sample.sajilo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sample.sajilo.BottomFragments.MyBookingFragment;
import com.sample.sajilo.BottomFragments.MyRideFragment;
import com.sample.sajilo.BottomFragments.VideoFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout mainActivityLayout;
    Fragment selectedFragment = null;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(this::mBottomNavigationBar).start();
        toolbar=findViewById(R.id.toolbar);
    }

    private void mBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            boolean bool = false;
            if (bottomNavigationView.getSelectedItemId() != item.getItemId()) {
                switch (item.getItemId()) {
                    case R.id.home:
                        bool = true;
                        Intent intent = new Intent(MainActivity.this, MyProfile.class);
                        startActivity(intent);
                        break;
                    case R.id.my_booking:
                        selectedFragment = new MyBookingFragment();
                        bool = true;
                        break;
                    case R.id.video:
                        selectedFragment = new VideoFragment();
                        toolbar.setVisibility(View.GONE);
                        bool = true;
                        break;
                    case R.id.my_ride:
                        selectedFragment = new MyRideFragment();
                        bool = true;
                        break;
                    case R.id.shop_now:
                        Intent intent1 = new Intent(MainActivity.this, ShopNow.class);
                        startActivity(intent1);
                        bool = true;
                        break;
                }
            }
            if (selectedFragment != null) {
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
            return bool;
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}