package com.example.healthproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.healthproject.Activity.UpdateEvent;
import com.example.healthproject.Model.dto.Event;
import com.example.healthproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class ManageFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_manage, container, false);
        recyclerView = x.findViewById(R.id.eventRecycler);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Query
        Query query = firebaseFirestore.collection("events");

        //Recycler Options
        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class).build();

        adapter = new FirestoreRecyclerAdapter<Event, EventsViewHolder>(options) {
//            private Filter filter = new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence constraint) {
//                    return null;
//                }
//
//                @Override
//                protected void publishResults(CharSequence constraint, FilterResults results) {
//
//                }
//            }
            @NonNull
            @Override
            public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_manage_item, parent, false);
                return new EventsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final EventsViewHolder holder, int position, @NonNull final Event event) {
                holder.name.setText(event.getName());
                holder.location.setText(event.getLocation());
                holder.date.setText(event.getDate() + "");
                holder.attendees.setText("Attendees:" + " " + event.getAttendees() + "");
                holder.interested.setText("Interested:" + " " + event.getInterested() + "");
                holder.duration.setText( "Time" + " " + event.getStart() + " - " + event.getEnd()+"" );
                holder.spaces.setText("Spaces:" + " " + event.getSpaces() + "");
                Log.d("Debug", event.getId());
                holder.manage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // set new Intent with event id -> load event data
                        Intent intent = new Intent(getActivity(), UpdateEvent.class);
                        intent.putExtra("ID", event.getId());
                        startActivity(intent);
                    }
                });

            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return x;
    }


    private class EventsViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView location;
        private TextView date;
        private TextView attendees;
        private TextView interested;
        private TextView spaces;
        private TextView duration;
        private Button manage;
        private TextView filter;

        private EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_manage_name);
            location = itemView.findViewById(R.id.list_manage_location);
            date = itemView.findViewById(R.id.list_manage_date);
            attendees = itemView.findViewById(R.id.list_manage_attendees);
            interested = itemView.findViewById(R.id.list_manage_interested);
            spaces = itemView.findViewById(R.id.list_manage_space);
            duration = itemView.findViewById(R.id.list_manage_duration);
            manage = itemView.findViewById(R.id.list_button_manage);
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
