package edu.mohibmir.covider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.lang.Object;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class first_fragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;

    public first_fragment() {
        // Required empty public constructor
        super(R.layout.fragment_first_fragment);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //get latlong for corners for specified place
        LatLng one = new LatLng(34.023595, -118.289880);
        LatLng two = new LatLng(34.018840, -118.282146);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //add them to builder
        builder.include(one);
        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.20);

        //set latlong bounds
        mMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);
    }
}