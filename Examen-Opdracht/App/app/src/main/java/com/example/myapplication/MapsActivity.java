package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Circle lastClickedCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng UserPosition = new LatLng(51.884573, 4.494143);
        ArrayList<LatLng> catPositions = new ArrayList<>();
        catPositions.add(new LatLng(51.884573, 4.494143));
        catPositions.add(new LatLng(51.884248, 4.494434));
        catPositions.add(new LatLng(51.883873, 4.494298));
        catPositions.add(new LatLng(51.884171, 4.493095));

        MarkerOptions userMarker = new MarkerOptions().position(UserPosition).title("U");
        mMap.addMarker(userMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UserPosition, 17));
        mMap.addPolyline(new PolylineOptions().addAll(catPositions).width(20).color(Color.BLACK).zIndex(1));

        // Add circles at each point
        for (LatLng position : catPositions) {
            CircleOptions circleOptions = new CircleOptions()
                    .center(position)
                    .radius(2.5) // Radius in meters
                    .strokeColor(Color.BLACK)
                    .fillColor(Color.BLACK)
                    .clickable(true)
                    .zIndex(2); // Make the circle clickable
            Circle circle = mMap.addCircle(circleOptions);

            // Store some tag or metadata if necessary
            circle.setTag(position);
        }

        // Set a click listener for the circles
        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                // Call your method here
                onCircleClicked(circle);
            }
        });
    }

    // Your method to handle the circle click
    public void onCircleClicked(Circle circle) {
        // Change the color of the last clicked circle back to the default color
        if (lastClickedCircle != null) {
            lastClickedCircle.setFillColor(Color.BLACK); // Change this to your default color
            lastClickedCircle.setStrokeColor(Color.BLACK); // Change this to your default color
        }

        // Change the color of the current clicked circle to red
        circle.setFillColor(Color.RED);
        circle.setStrokeColor(Color.RED);

        // Update the last clicked circle to the current one
        lastClickedCircle = circle;

        LatLng position = (LatLng) circle.getTag();
        Log.d("CircleClicked", "Circle clicked at: " + position);
        // Add any additional logic here
    }
}