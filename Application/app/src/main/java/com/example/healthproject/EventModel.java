package com.example.healthproject;

public class EventModel {

    private long attendees;
    private long interested;
    private long spaces;
    private String link;
    private String name;
    private String location;

    private EventModel() {

    }

    private EventModel(long attendees, long interested, long spaces, String link, String name, String location) {
        this.attendees = attendees;
        this.interested = interested;
        this.spaces = spaces;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
