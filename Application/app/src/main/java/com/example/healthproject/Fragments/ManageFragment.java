package com.example.healthproject.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.healthproject.Model.dto.EventModel;
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


public class ManageFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView mFirestoreList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_manage, container, false);
        mFirestoreList = x.findViewById(R.id.eventRecycler);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Query
        Query query = firebaseFirestore.collection("events");

        //Recycler Options
        FirestoreRecyclerOptions<EventModel> options = new FirestoreRecyclerOptions.Builder<EventModel>()
                .setQuery(query, EventModel.class).build();

        adapter = new FirestoreRecyclerAdapter<EventModel, EventsManageViewHolder>(options) {
            @NonNull
            @Override
            public EventsManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_2, parent, false);
                return new EventsManageViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final EventsManageViewHolder holder, int position, @NonNull final EventModel model) {
                holder.name.setText(model.getName());
                holder.location.setText(model.getLocation());
                holder.date.setText(model.getDate() + "");
                holder.attendees.setText("Attendees:" + " " + model.getAttendees() + "");
                holder.interested.setText("Interested:" + " " + model.getInterested() + "");
                holder.spaces.setText("Spaces:" + " " + model.getSpaces() + "");

                holder.manage.setOnClickListener(new View.OnClickListener() {
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
                                    model.setPushId(pushId);
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


    private static class EventsManageViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView location;
        private TextView date;
        private TextView attendees;
        private TextView interested;
        private TextView spaces;
        private TextView duration;
        private Button manage;

        public EventsManageViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_event)



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
