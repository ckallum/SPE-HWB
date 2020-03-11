package com.example.healthproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthproject.R;


public class VenuesFragment extends Fragment {
    ImageView indoor;
    ImageView coombe;
    ImageView su;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_venues,container,false);

        indoor = rootView.findViewById(R.id.indoor);
        coombe = rootView.findViewById(R.id.coombe);
        su = rootView.findViewById(R.id.richmond);

        indoor.setClickable(true);
        coombe.setClickable(true);
        su.setClickable(true);

        indoor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), "indoor", Toast.LENGTH_SHORT).show();
            }
        });

        coombe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), "coombe", Toast.LENGTH_SHORT).show();
            }
        });

        su.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), "su", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
