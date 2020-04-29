package com.example.healthproject.Model.dto;

public class Event {

    private long attendees;
    private long interested;
    private long spaces;
    private String description;
    private String start;
    private String end;
    private String date;
    private String name;
    private String location;
    private String id;

    public Event(Integer attendees, Integer interested, Integer spaces, String date, String name, String location, String start, String end, String description) {
        this.attendees = attendees;
        this.interested = interested;
        this.spaces = spaces;
        this.date = date;
        this.start = start;
        this.end = end;
        this.description = description;
        this.name = name;
        this.location = location;
    }

    public Event() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getId() {
        return this.id;
    }

    public void setId ( String id ){
        this.id = id;
    }

}
