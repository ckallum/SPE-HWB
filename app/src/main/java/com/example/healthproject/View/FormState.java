package com.example.healthproject.View;

import androidx.annotation.Nullable;

/**
 * Data validation state of the forgot form.
 */
public class FormState {
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer eventNameError;
    @Nullable
    private Integer eventDateError;
    @Nullable
    private Integer eventStartTimeError;
    @Nullable
    private Integer eventEndTimeError;
    @Nullable
    private Integer eventAttendeeError;
    @Nullable
    private boolean isDataValid;

    public FormState(@Nullable Integer emailError, @Nullable Integer passwordError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    public FormState(@Nullable Integer eventNameError, @Nullable Integer eventStartTimeError, @Nullable Integer eventEndTimeError,@Nullable Integer eventDateError, @Nullable Integer eventAttendeeError) {
        this.eventNameError = eventNameError;
        this.eventStartTimeError = eventStartTimeError;
        this.eventEndTimeError = eventEndTimeError;
        this.eventDateError = eventDateError;
        this.eventAttendeeError = eventAttendeeError;
        this.isDataValid = false;
    }

    public FormState(boolean isDataValid) {
        this.emailError = null;
        this.passwordError = null;
        this.eventAttendeeError = null;
        this.eventDateError = null;
        this.eventNameError = null;
        this.eventStartTimeError = null;
        this.eventEndTimeError = null;
        this.isDataValid = isDataValid;
    }

    public FormState(@Nullable Integer emailError) {
        this.emailError = emailError;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    @Nullable
    public Integer getEventNameError() {
        return eventNameError;
    }

    @Nullable
    public Integer getEventDateError() {
        return eventDateError;
    }

    @Nullable
    public Integer getEventStartTimeError() {
        return eventStartTimeError;
    }
    public Integer getEventEndTimeError() {
        return eventEndTimeError;
    }


    @Nullable
    public Integer getEventAttendeeError() {
        return eventAttendeeError;
    }

    @Nullable
    public boolean getEventError(){
        return (getEventNameError() != null ||getEventDateError() != null ||getEventStartTimeError() != null || getEventEndTimeError() != null || getEventAttendeeError() != null);
    }
}
