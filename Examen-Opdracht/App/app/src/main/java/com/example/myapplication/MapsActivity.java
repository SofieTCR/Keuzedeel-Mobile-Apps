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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Circle lastClickedCircle;
    private CatList catlist;

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng UserPosition = new LatLng(51.884573, 4.494143);

        catlist = getCatList();

        MarkerOptions userMarker = new MarkerOptions().position(UserPosition).title("U");
        mMap.addMarker(userMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UserPosition, 17));

        // Add circles at each point
        int index = 0;
        PolylineOptions lineOptions = new PolylineOptions().width(20).color(Color.DKGRAY).zIndex(1);
        for (CatData data : catlist.cats) {
            lineOptions.add(data.position);

            CircleOptions circleOptions = new CircleOptions()
                    .center(data.position)
                    .radius(2.5) // Radius in meters
                    .strokeColor(Color.DKGRAY)
                    .fillColor(Color.DKGRAY)
                    .clickable(true)
                    .zIndex(2); // Make the circle clickable
            Circle circle = mMap.addCircle(circleOptions);

            // Store some tag or metadata if necessary
            circle.setTag(index);
            index++;
        }
        mMap.addPolyline(lineOptions);
        // Set a click listener for the circles
        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                // Call your method here
                onCircleClicked(circle);
            }
        });
    }

    public CatList getCatList() {
        CatList catList = new CatList("Nyx");

        catList.AddCatData(new CatData(
                new LatLng(51.884573, 4.494143),
                "10:01 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.884248, 4.494434),
                "10:03 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.883873, 4.494298),
                "10:08 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.884171, 4.493095),
                "10:13 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.884091, 4.492711),
                "10:15 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.883517, 4.493090),
                "10:17 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.883320, 4.494497),
                "10:26 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.883684, 4.493495),
                "10:30 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.884377, 4.492529),
                "10:37 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.884408, 4.492870),
                "10:42 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.884861, 4.492893),
                "10:45 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.885485, 4.493907),
                "10:48 18-06-2024"));
        catList.AddCatData(new CatData(new LatLng(51.885624, 4.495075),
                "10:56 18-06-2024"));
        return catList;
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

        int index = (int) circle.getTag();
        CatData data = catlist.cats.get(index);

        RelativeLayout layout = findViewById(R.id.rounded_rectangle);
        layout.setVisibility(RelativeLayout.VISIBLE);

        TextView latitude = findViewById(R.id.LatitudeWindow);
        latitude.setText(String.format("Lat: %s", data.position.latitude));
        TextView longitude = findViewById(R.id.LongitudeWindow);
        longitude.setText(String.format("Lon: %s", data.position.longitude));
        TextView window = findViewById(R.id.Window);
        window.setText(String.format("%s: %s", catlist.name, data.dateTime));
        TextView distance = findViewById(R.id.kmWindow);
        distance.setText(String.format("%sKm", (float) Math.round(catlist.getDistance() * 10) / 10));
    }
}