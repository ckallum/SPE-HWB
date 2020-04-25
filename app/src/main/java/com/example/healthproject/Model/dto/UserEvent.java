package com.example.healthproject.Model.dto;

public class UserEvent {
    private String userId;
    private String eventId;

    public UserEvent(String uId, String eId) {
        this.userId = uId;
        this.eventId = eId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
