package com.sample.sajilo.BottomFragments;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sample.sajilo.Adapter.VideoAdapter;
import com.sample.sajilo.Common.ConstantClass;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.Model.Datum;
import com.sample.sajilo.Model.VideoResponse;
import com.sample.sajilo.R;
import com.sample.sajilo.databinding.FragmentHomeBinding;
import com.sample.sajilo.databinding.FragmentVideoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoFragment extends Fragment {
    private FragmentVideoBinding view;
    String Video_url= ConstantClass.Base_Url+"vediolist.php";
    ArrayList<VideoResponse> videoSlider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = FragmentVideoBinding.inflate(inflater, container, false);
        videoSlider=new ArrayList<>();
        if(NetworkConnection.isConnected(getActivity())){
            getAllVideoData();
        }
        else{
            Toast.makeText(getActivity(), "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
        }
        return view.getRoot();
    }

    private void getAllVideoData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Video_url, null, response -> {
            try {
                JSONArray jsonArray=response.getJSONArray("data");
                if(response.getString("Result").equalsIgnoreCase("true")){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String id=jsonObject.getString("id");
                        String vedio=jsonObject.getString("vedio");
                        String status=jsonObject.getString("status");
                        String title=jsonObject.getString("title");
                        String description=jsonObject.getString("description");
                        String date=jsonObject.getString("date");
                        VideoResponse videoResponse=new VideoResponse(id,vedio,status,date,title,description);
                        videoSlider.add(videoResponse);
                    }
                    view.viewpager.setAdapter(new VideoAdapter(videoSlider));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(jsonObjectRequest);
    }
}