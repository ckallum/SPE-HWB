package com.example.healthproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthproject.R;


public class VenuesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_venues,container,false);

        ImageView indoor = rootView.findViewById(R.id.indoor);
        ImageView coombe = rootView.findViewById(R.id.coombe);
        ImageView su = rootView.findViewById(R.id.richmond);

        indoor.setClickable(true);
        coombe.setClickable(true);
        su.setClickable(true);

        indoor.setOnClickListener(v -> {
            Fragment someFragment = new IndoorFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        });

        coombe.setOnClickListener(v -> {
            Fragment someFragment = new CoombeFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
            transaction.addToBackStack(null);  // if written, this transaction will be added to
            transaction.commit();
        });

        su.setOnClickListener(v -> {
            Fragment someFragment = new SuFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
            transaction.addToBackStack(null);  // if written, this transaction will be added to
            transaction.commit();
        });

        return rootView;
    }


}
