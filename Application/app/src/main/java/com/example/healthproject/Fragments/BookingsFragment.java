package com.example.healthproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthproject.Event;
import com.example.healthproject.FirebaseDatabaseHelper;
import com.example.healthproject.R;
import com.example.healthproject.RecyclerViewConfig;

import java.util.List;

public class BookingsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_booking,container,false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.firestore_events2);

        new FirebaseDatabaseHelper().readEvents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Event> events, List<String> keys) {
                new RecyclerViewConfig().setConfig(mRecyclerView, getContext(), events, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        return rootView;
    }
}
