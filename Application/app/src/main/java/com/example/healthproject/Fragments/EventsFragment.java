package com.example.healthproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthproject.EventModel;
import com.example.healthproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class EventsFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView mFirestoreList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View x =  inflater.inflate(R.layout.fragment_events,container,false);
        mFirestoreList = x.findViewById(R.id.firestore_events);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Query
        Query query = firebaseFirestore.collection("events");

        //Recycler Options
        FirestoreRecyclerOptions<EventModel> options = new FirestoreRecyclerOptions.Builder<EventModel>()
                .setQuery(query, EventModel.class).build();

        adapter = new FirestoreRecyclerAdapter<EventModel, EventsViewHolder>(options) {
            @NonNull
            @Override
            public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new EventsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull EventsViewHolder holder, int position, @NonNull EventModel model) {
                holder.list_name.setText(model.getName());
                holder.list_location.setText(model.getLocation());
                holder.list_link.setText(model.getLink());
                holder.list_attendees.setText(model.getAttendees() + "");
                holder.list_interested.setText(model.getInterested() + "");
                holder.list_spaces.setText(model.getSpaces() + "");
            }
        };

        mFirestoreList.setHasFixedSize(true);
//        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

        return x;
    }


    private class EventsViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private TextView list_location;
        private TextView list_link;
        private TextView list_attendees;
        private TextView list_interested;
        private TextView list_spaces;


        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.list_name);
            list_location = itemView.findViewById(R.id.list_location);
            list_link = itemView.findViewById(R.id.list_link);
            list_attendees = itemView.findViewById(R.id.list_attendees);
            list_interested = itemView.findViewById(R.id.list_interested);
            list_spaces = itemView.findViewById(R.id.list_spaces);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
