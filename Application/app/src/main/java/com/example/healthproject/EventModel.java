package com.example.healthproject;
import com.google.firebase.Timestamp;

public class EventModel {

    private long attendees;
    private long interested;
    private long spaces;
    private Timestamp date;
    private String name;
    private String location;
    private String pushId;

    private EventModel() {

    }

    private EventModel(long attendees, long interested, long spaces, Timestamp date, String name, String location) {
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

    public void setDate(Timestamp link) {
        this.date = link;
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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
