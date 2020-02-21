package com.example.healthproject;

import android.widget.Toast;

import com.google.firebase.Timestamp;

public class Event {

    private long attendees;
    private long interested;
    private long spaces;
    private Timestamp date;
    private String name;
    private String location;

    public Event() {
    }

    public Event(long attendees, long interested, long spaces, Timestamp date, String name, String location) {
        this.attendees = attendees;
        this.interested = interested;
        this.spaces = spaces;
        this.date = date;
        this.name = name;
        this.location = location;
    }

    public long getAttendees() {
        return attendees;
    }

    public void setAttendees(long attendees) {
        this.attendees = attendees;
    }

    public long getInterested() {
        return interested;
    }

    public void setInterested(long interested) {
        this.interested = interested;
    }

    public long getSpaces() {
        return spaces;
    }

    public void setSpaces(long spaces) {
        this.spaces = spaces;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
