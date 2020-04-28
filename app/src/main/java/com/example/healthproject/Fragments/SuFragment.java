package com.example.healthproject.Fragments;

import android.content.Intent;
import android.net.Uri;
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

public class SuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_su, container, false);

        ImageView backArrow = rootView.findViewById(R.id.back);
        ImageView map = rootView.findViewById(R.id.sumap);

        backArrow.setClickable(true);
        map.setClickable(true);

        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("geo:51.457437, -2.612887?q= Richmond+Building, 105+Queens+Rd, Bristol");
        final Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");


        backArrow.setOnClickListener(v -> {
            Fragment someFragment = new VenuesFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(((ViewGroup) getView().getParent()).getId(), someFragment); // give your fragment container id in first parameter
            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        });

        map.setOnClickListener(v -> {
//                openApp(getActivity(), "com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        return rootView;

    }

}
