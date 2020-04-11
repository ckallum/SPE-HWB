package com.example.healthproject.Fragments;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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
private Integer lastCount = 0;
private Integer stepGoalcount = 500;

Boolean running = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       final View v =inflater.inflate(R.layout.steps_fragment, container, false);

        steps = v.findViewById(R.id.tv_steps);
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        final TextView stepsGoal = v.findViewById(R.id.stepGoal);
        //final Button confirmSteps = v.findViewById(R.id.setButton);
        //final EditText chooseSteps = v.findViewById(R.id.goalSet);



        /*confirmSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!chooseSteps.getText().toString().equals("")){
                    stepsGoal.setText(chooseSteps.getText().toString());

                    confirmSteps.setClickable(false);
                }
            }


        });*/

        stepsGoal.setText(stepGoalcount.toString());




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


        if(stepGoalcount == stepCount || stepCount > stepGoalcount){
            stepCount = 0;

        }


        ProgressBar progressBar = v.findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 50, stepCount); //animate only from last known step to current step count
        animation.setDuration(5000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();



        return v;

    }

    public void onPause() {

        super.onPause();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor     =  sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepGoal",stepGoalcount);
        editor.putInt("stepcount",stepCount);
        editor.apply();
    }

    public void onStop() {

        super.onStop();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor     =  sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepGoal",stepGoalcount);
        editor.putInt("stepcount",stepCount);
        editor.apply();
    }

    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepcount",0);

        if(stepGoalcount == stepCount || stepCount > stepGoalcount){
            stepCount = 0;
            stepCount = sharedPreferences.getInt("steacount",0);
        }
        stepGoalcount = sharedPreferences.getInt("stepGoal", 0);

    }

}




