package com.example.healthproject.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.healthproject.Model.dto.Event;
import com.example.healthproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;



public class EventsFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView mFirestoreList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_events, container, false);
        mFirestoreList = x.findViewById(R.id.firestore_events);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Query
        Query query = firebaseFirestore.collection("events");

        //Recycler Options
        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class).build();

        adapter = new FirestoreRecyclerAdapter<Event, EventsViewHolder>(options) {
            @NonNull
            @Override
            public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new EventsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final EventsViewHolder holder, int position, @NonNull final Event model) {
                holder.list_name.setText(model.getName());
                holder.list_location.setText(model.getLocation());
                holder.list_date.setText(model.getDate() + "");
                holder.list_attendees.setText("Attendees:" + " " + model.getAttendees() + "");
                holder.list_interested.setText("Interested:" + " " + model.getInterested() + "");
                holder.list_spaces.setText("Spaces:" + " " + model.getSpaces() + "");
                holder.list_duration.setText("Time:" + " " + model.getStart() + " - "+ model.getEnd());


                holder.interested.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // do something
                        //Gets current user to save the event
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        final DatabaseReference eventRef = rootRef.child(uid).child(model.getName());

                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    DatabaseReference pushRef = eventRef.push();
                                    String pushId = pushRef.getKey();
                                    model.setId(pushId);
                                    pushRef.setValue(model);

                                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Already saved", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("stefan", databaseError.getMessage());
                            }
                        };

                        eventRef.addListenerForSingleValueEvent(eventListener);


                    }
                });

            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFirestoreList.setAdapter(adapter);

        return x;
    }


    private class EventsViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private TextView list_location;
        private TextView list_date;
        private TextView list_attendees;
        private TextView list_interested;
        private TextView list_spaces;
        private TextView list_duration;
        private TextView interested;
        private SearchView filter;


        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.list_name);
            list_location = itemView.findViewById(R.id.list_location);
            list_date = itemView.findViewById(R.id.list_date);
            list_attendees = itemView.findViewById(R.id.list_attendees);
            list_interested = itemView.findViewById(R.id.list_interested);
            list_spaces = itemView.findViewById(R.id.list_spaces);
            list_duration = itemView.findViewById(R.id.list_duration);
            interested = itemView.findViewById(R.id.button_interested);
            filter = itemView.findViewById(R.id.eventFilter);
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
