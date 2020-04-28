package com.example.healthproject.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.example.healthproject.Model.dto.Event;
import com.example.healthproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class BookingsFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter<Event, EventsViewHolder> adapter;
    private GlobalUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_booking, container, false);
        RecyclerView mRecyclerView = x.findViewById(R.id.firestore_events);
        user = GlobalUser.getInstance(new FirebaseDataSource());
        firebaseFirestore = FirebaseFirestore.getInstance();
        SearchView filter = x.findViewById(R.id.eventFilter);


        final ArrayList<String> eventIds = new ArrayList<>();
        CollectionReference links = firebaseFirestore.collection("user_event_link");
        links.whereEqualTo("userId", user.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Log.d("Success", document.getId() + " => " + document.getData());
                        eventIds.add(Objects.requireNonNull(document.get("eventId")).toString());
                    }
                    if ( !eventIds.isEmpty() ){
                        Query query = firebaseFirestore.collection("events").whereIn("id", eventIds);
                        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                                .setQuery(query, Event.class).build();
                        adapter.updateOptions(options);
                    }


                } else {
                    Log.d("Fail", "Error getting documents: ", task.getException());
                }

            }

        });


        filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Query query;
                if (eventIds.isEmpty()) {
                    return false;

                }
                query = firebaseFirestore.collection("events").orderBy("name").startAt(newText).endAt(newText + "\uf8ff").whereIn("id", eventIds);
                FirestoreRecyclerOptions<Event> newOptions = new FirestoreRecyclerOptions.Builder<Event>()
                        .setQuery(query, Event.class).build();
                adapter.updateOptions(newOptions);
                return false;
            }

        });


        Query query = firebaseFirestore.collection("null");
        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class).build();
        adapter = new FirestoreRecyclerAdapter<Event, EventsViewHolder>(options) {
            @NonNull
            @Override
            public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
                return new EventsViewHolder(view);
            }

            @Override
            public void updateOptions(@NonNull FirestoreRecyclerOptions<Event> options) {
                super.updateOptions(options);
            }

            @Override
            protected void onBindViewHolder(@NonNull final EventsViewHolder holder, int position, @NonNull final Event event) {
                holder.name.setText(event.getName());
                holder.location.setText(event.getLocation());
                holder.date.setText(event.getDate() + "");
                holder.attendees.setText("Attendees:" + " " + event.getAttendees() + "");
                holder.interested.setText("Interested:" + " " + event.getInterested() + "");
                holder.duration.setText("Time" + " " + event.getStart() + " - " + event.getEnd() + "");
                holder.spaces.setText("Spaces:" + " " + event.getSpaces() + "");
                Log.d("Debug", event.getId());
                holder.unsubscribe.setOnClickListener(v -> {
                    // set new Intent with event id -> load event data
                    user.unsubscribeEvent(event.getId());
                });
            }

        };
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return x;
    }


    private static class EventsViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView location;
        private TextView date;
        private TextView attendees;
        private TextView interested;
        private TextView spaces;
        private TextView duration;
        private Button unsubscribe;

        private EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_name);
            location = itemView.findViewById(R.id.list_location);
            date = itemView.findViewById(R.id.list_date);
            attendees = itemView.findViewById(R.id.list_attendees);
            interested = itemView.findViewById(R.id.list_interested);
            spaces = itemView.findViewById(R.id.list_spaces);
            duration = itemView.findViewById(R.id.list_duration);
            unsubscribe = itemView.findViewById(R.id.button_unsubscribe);

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
