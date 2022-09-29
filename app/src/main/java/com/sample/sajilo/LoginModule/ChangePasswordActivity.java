package com.sample.sajilo.LoginModule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sample.sajilo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    String url = "https://adminapp.tech/Sajilo/capi/updatepassword.php";
    String email, mobile, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        try {
            getUserData(email, mobile, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserData(String email, String mobile, String password) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray1 = new JSONArray();
                JSONArray jsonArray = new JSONArray();
                try {
                    Log.d("TAG", "onResponse: " + jsonArray1);
                    JSONObject jsonObject = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);
                return params;
            }
        };
        queue.add(request);
    }
}