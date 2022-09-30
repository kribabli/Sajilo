package com.sample.sajilo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sample.sajilo.BottomFragments.HomeFragment;
import com.sample.sajilo.BottomFragments.MyBookingFragment;
import com.sample.sajilo.BottomFragments.MyRideFragment;
import com.sample.sajilo.BottomFragments.ShopNowFragment;
import com.sample.sajilo.BottomFragments.VideoFragment;
import com.sample.sajilo.Common.HelperData;
import com.sample.sajilo.LoginModule.LoginActvity;
import com.sample.sajilo.ServicesRelatedFragment.myServicesFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawerLayout;
    Toolbar toolbar_main;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    HelperData helperData;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(this::mBottomNavigationBar).start();
        fragmentManager = getSupportFragmentManager();
        helperData=new HelperData(getApplicationContext());
        toolbar_main = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);
        initMethod();
        setAction();
        googleSignIn();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_main, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        toolbar_main.setNavigationIcon(R.drawable.ic_baseline_format_list_bulleted_24);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.menu_go_home:
                    HomeFragment homeFragment = new HomeFragment();
                    loadfragment(homeFragment, getString(R.string.menu_home), fragmentManager);
                    return true;
                case R.id.menu_go_to_profile:
                    Intent intent = new Intent(MainActivity.this, MyProfile.class);
                    startActivity(intent);
                    return true;

                case R.id.menu_go_logout:
                    userlogout();
                    return true;
                default:
                    return true;
            }
        });
        HomeFragment homeFragment = new HomeFragment();
        loadfragment(homeFragment, "Home", fragmentManager);
    }

    private void userlogout() {
        Intent intent = new Intent(getApplicationContext(), LoginActvity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        gsc.signOut();
        helperData.Logout();
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
    }

    private void initMethod() {
        drawerLayout = findViewById(R.id.drawerLayout);
    }

    private void setAction() {
    }

    private void mBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            boolean bool = false;
            if (bottomNavigationView.getSelectedItemId() != item.getItemId()) {
                switch (item.getItemId()) {
                    case R.id.home:
                        bool = true;
                        HomeFragment homeFragment1 = new HomeFragment();
                        loadfragment(homeFragment1, "Home", fragmentManager);
                        break;
                    case R.id.my_booking:
                        MyBookingFragment homeFragment = new MyBookingFragment();
                        loadfragment(homeFragment, "My Booking", fragmentManager);
                        bool = true;
                        break;
                    case R.id.video:
                        VideoFragment videoFragment = new VideoFragment();
                        loadfragment(videoFragment, "Video", fragmentManager);
                        bool = true;
                        break;
                    case R.id.my_ride:
                        MyRideFragment myRideFragment = new MyRideFragment();
                        loadfragment(myRideFragment, "My Ride", fragmentManager);
                        bool = true;
                        break;
                    case R.id.shop_now:
                        ShopNowFragment shopNowFragment = new ShopNowFragment();
                        loadfragment(shopNowFragment, "Shop Now", fragmentManager);
                        bool = true;
                        break;
                }
            }

            return bool;
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void loadfragment(Fragment f1, String name, FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, f1, name);
        ft.commit();
        setToolbarTitle(name);
    }

    public void setToolbarTitle(String Title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(Title);
        }
    }

    private void googleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            String userName = googleSignInAccount.getDisplayName();
            String userEmail = googleSignInAccount.getEmail();
            Uri photoUrl = googleSignInAccount.getPhotoUrl();
            String id = googleSignInAccount.getId();
            Log.d("TAG", "onCreate: " + id + "  " + userName + "  " + userEmail + "  " + photoUrl);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() != 0) {
            String tag = fragmentManager.getFragments().get(fragmentManager.getBackStackEntryCount() - 1).getTag();
            setToolbarTitle(tag);
            super.onBackPressed();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.back_key), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }
    }
}
