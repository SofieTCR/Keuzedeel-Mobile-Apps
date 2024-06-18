package com.example.myapplication;

import com.google.android.gms.maps.model.LatLng;

import java.time.Instant;
import java.time.LocalDateTime;

public class CatData {
    public LatLng position;
    public String dateTime;

    public CatData(LatLng position, String dateTime) {
        this.position = position;
        this.dateTime = dateTime;
    }
}

