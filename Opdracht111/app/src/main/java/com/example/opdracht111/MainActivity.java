package com.example.opdracht111;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SensorTest(View view) {
        // this is the management system for all possible the device's sensors.
        SensorManager sensorManager;
        // We grab all the active sensors from the device.
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        // Store all the sensors in the list
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : sensors)
        {
            Log.i("Sensor: ", sensor.getName());

            if (sensor.getType() == Sensor.TYPE_LIGHT)
            {
                Log.i("Light: ", sensor.getName());
            }
        }
    }
}