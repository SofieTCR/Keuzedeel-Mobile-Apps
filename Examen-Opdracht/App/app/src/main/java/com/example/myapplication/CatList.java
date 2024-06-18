package com.example.myapplication;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class CatList {
    public String name;
    public ArrayList<CatData> cats = new ArrayList<CatData>();

    public CatList(String name) {
        this.name = name;
    }

    public void AddCatData(CatData catData) {
        cats.add(catData);
    }

    private double haversineDistance(LatLng point1, LatLng point2) {
        double lat1 = Math.toRadians(point1.latitude);
        double lon1 = Math.toRadians(point1.longitude);
        double lat2 = Math.toRadians(point2.latitude);
        double lon2 = Math.toRadians(point2.longitude);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6371.0 * c;
    }
    public double getDistance() {
        double totalDistance = 0.0;

        if (cats == null || cats.size() < 2) {
            return totalDistance;
        }

        for (int i = 1; i < cats.size(); i++) {
            LatLng point1 = cats.get(i - 1).position;
            LatLng point2 = cats.get(i). position;
            totalDistance += haversineDistance(point1, point2);
        }

        return totalDistance;
    }
}
