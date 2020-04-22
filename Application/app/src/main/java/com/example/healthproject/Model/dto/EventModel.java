package com.example.healthproject.Model.dto;

public class EventModel {

    private Integer attendees;
    private Integer interested;
    private Integer spaces;
    private String date;
    private String name;
    private String location;
    private String pushId;

    private EventModel() {

    }

    private EventModel(Integer attendees, Integer interested, Integer spaces, String date, String name, String location) {
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

    public void setAttendees(Integer attendees) {
        this.attendees = attendees;
    }

    public long getInterested() {
        return interested;
    }

    public void setInterested(Integer interested) {
        this.interested = interested;
    }

    public long getSpaces() {
        return spaces;
    }

    public void setSpaces(Integer spaces) {
        this.spaces = spaces;
    }

    public String  getDate() {
        return date;
    }

    public void setDate(String link) {
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
