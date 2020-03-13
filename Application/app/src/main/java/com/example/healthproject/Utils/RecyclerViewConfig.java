package com.example.healthproject.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthproject.Model.dto.Event;
import com.example.healthproject.R;

import java.util.List;

public class RecyclerViewConfig {
    private Context mContext;
    private EventsAdapter mEventsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Event> events, List<String> keys) {
        mContext = context;
        mEventsAdapter = new EventsAdapter(events, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mEventsAdapter);
    }

    class EventItemView extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mDate;
        private TextView mLocation;
        private TextView mSpaces;
        private TextView mInterested;
        private TextView mAttendees;

        private String key;

        public EventItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext)
                    .inflate(R.layout.event_item, parent, false));

            mName = (TextView) itemView.findViewById(R.id.list_name2);
            mDate = (TextView) itemView.findViewById(R.id.list_date2);
            mLocation = (TextView) itemView.findViewById(R.id.list_location2);
            mSpaces = (TextView) itemView.findViewById(R.id.list_spaces2);
            mInterested = (TextView) itemView.findViewById(R.id.list_interested2);
            mAttendees = (TextView) itemView.findViewById(R.id.list_attendees2);
        }

        public void bind(Event event, String key) {
            mName.setText(event.getName());
            mDate.setText(event.getDate() + "");
            mLocation.setText(event.getLocation() + "");
            mSpaces.setText("Spaces: " + event.getSpaces());
            mInterested.setText("Interested: " + event.getInterested());
            mAttendees.setText("Attendees: " + event.getAttendees());
            this.key = key;

        }
    }

    class EventsAdapter extends RecyclerView.Adapter<EventItemView>{
        private List<Event> mEventList;
        private List<String> mKeys;

        public EventsAdapter(List<Event> mEventList, List<String> mKeys) {
            this.mEventList = mEventList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public EventItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EventItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EventItemView holder, int position) {
            holder.bind(mEventList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mEventList.size();
        }
    }
}
