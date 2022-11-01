package com.sample.sajilo.BottomFragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sample.sajilo.Common.NetworkConnection;
import com.sample.sajilo.R;
import com.sample.sajilo.databinding.FragmentMyBookingBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyBookingFragment extends Fragment {

    private FragmentMyBookingBinding view;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= FragmentMyBookingBinding.inflate(inflater, container, false);
        view.searchView.clearFocus();
        view.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText);
                return true;
            }
        });
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getContext());
        if (NetworkConnection.isConnected(getActivity())) {
            getLastLocation();
        } else {
            Toast.makeText(getActivity(), "No Network Connection!!!..", Toast.LENGTH_SHORT).show();
        }

        return view.getRoot();
    }

    private void getLastLocation() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        Geocoder geocoder=new Geocoder(getContext(), Locale.getDefault());
                        try {
//                            Log.d("Amit","Value A "+location);
                            List<Address> addressList=geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            view.addressText.setText(" "+addressList.get(0).getSubLocality()+","+addressList.get(0).getLocality());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                }
            });



        }

    }

    private void filterText(String text) {


    }


}