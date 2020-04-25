package com.example.healthproject.Model.dto;

import com.google.firebase.Timestamp;

public class VenueModel {
    private String name;
    private int events;
    private Timestamp opening;
    private Timestamp closing;

    public void setName(String name) {
        this.name = name;
    }

    public void setEvents(int events) {
        this.events = events;
    }

    public void setOpening(Timestamp opening) {
        this.opening = opening;
    }

    public void setClosing(Timestamp closing) {
        this.closing = closing;
    }

    public String getName() {
        return name;
    }

    public int getEvents() {
        return events;
    }

    public Timestamp getOpening() {
        return opening;
    }

    public Timestamp getClosing() {
        return closing;
    }



}
