package com.sample.sajilo.Common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class HelperData extends Application {
    private static String SHARED_PREF_NAME1 = "Sajilo";
    private static String SHARED_PREF_NAME2 = "profileImage";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public HelperData(Context context) {
        this.context = context;
    }

    public void saveIsLogin(boolean flag) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("IsLoggedIn", flag);
        editor.apply();
    }

    public boolean getIsLogin() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsLoggedIn", false);
    }

    public void saveIsRemember(boolean flag) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("IsLoggedRemember", flag);
        editor.apply();
    }

    public boolean getIsRemember() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsLoggedRemember", false);
    }

    public void saveRemember(String email, String password) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("remember_email", email);
        editor.putString("remember_password", password);
        editor.apply();
    }

    public String getRememberEmail() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("remember_email", "");
    }

    public String getRememberPassword() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("remember_password", "");
    }

    public void saveLogin(String user_id, String user_name, String email,String mobile,String refferal_code) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("user_id", user_id);
        editor.putString("user_name", user_name);
        editor.putString("email", email);
        editor.putString("mobile", mobile);
        editor.putString("refferal_code",refferal_code);
        editor.apply();
    }

    public String getUserId() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id", "");
    }

    public String getReferCode() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("refferal_code", "");
    }

    public String getUserName() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_name", "");
    }

    public String getUserEmail() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    public String getMobile() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("mobile", "");
    }

    public void Logout(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void saveProfileImage(Uri Image){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("UserprofileImage", String.valueOf(Image));
        editor.apply();
    }

    public Uri getUserProfileImage(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        return Uri.parse(sharedPreferences.getString("UserprofileImage", ""));
    }
}
