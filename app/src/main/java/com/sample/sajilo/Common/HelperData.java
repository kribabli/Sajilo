package com.sample.sajilo.Common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class HelperData extends Application {
    private static String SHARED_PREF_NAME1 = "Sajilo";
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

    public void saveLogin(String user_id, String user_name, String email) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("user_id", user_id);
        editor.putString("user_name", user_name);
        editor.putString("email", email);
        editor.apply();
    }


    public String getUserId() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id", "");
    }

    public String getUserName() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_name", "");
    }

    public String getUserEmail() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }



}
