package com.example.healthproject.Fragments;

import android.content.Context;
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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StepsFragment extends Fragment implements SensorEventListener {

SensorManager sensorManager;
Context context;
TextView steps;
Boolean running = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       final View v =inflater.inflate(R.layout.steps_fragment, container, false);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        steps = getActivity().findViewById(R.id.tv_steps);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        running = true;
        Sensor countS = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(countS != null){
            sensorManager.registerListener(this, countS,SensorManager.SENSOR_DELAY_UI);

        }else{
            Toast.makeText(getContext(),"Sensor not found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        running = false;
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(running){
            steps.setText(String.valueOf(event.values[0]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}




