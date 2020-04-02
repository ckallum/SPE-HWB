package com.example.healthproject.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.healthproject.R;

import java.util.ArrayList;

import static android.view.View.getDefaultSize;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class StepsFragment extends Fragment{

SensorManager sensorManager;
Context context;
private TextView steps;
private double previousMag = 0;
private Integer stepCount = 0;

Boolean running = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       final View v =inflater.inflate(R.layout.steps_fragment, container, false);

        steps = v.findViewById(R.id.tv_steps);
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event != null){
                    float x_acc = event.values[0];  //acceleration
                    float y_acc = event.values[1];
                    float z_acc = event.values[2];

                    double magnitude = Math.sqrt(x_acc*x_acc + y_acc*y_acc + z_acc* z_acc);
                    double magDelta = magnitude - previousMag;
                    previousMag = magnitude;

                    if(magDelta > 6){
                        stepCount++;
                    }
                    steps.setText(stepCount.toString());

                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(stepDetector,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        return v;

    }

    public void onPause() {

        super.onPause();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor     =  sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepcount",stepCount);
        editor.apply();
    }

    public void onStop() {

        super.onStop();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor     =  sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepcount",stepCount);
        editor.apply();
    }

    public void onResume() {  //set value when resume app, default 0. Maybe implement an alarm?
        super.onResume();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount",0);

    }



}




