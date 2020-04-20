package com.example.healthproject.View;

import androidx.annotation.Nullable;

/**
 * Data validation state of the forgot form.
 */
public class FormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer eventNameError;
    @Nullable
    private Integer eventDateError;
    @Nullable
    private Integer eventTimeError;
    @Nullable
    private Integer eventAttendeeError;
    @Nullable
    private Integer emailError;
    private boolean isDataValid;

    public FormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    public FormState(@Nullable Integer eventNameError, @Nullable Integer eventTimeError, @Nullable Integer eventDateError, @Nullable Integer eventAttendeeError) {
        this.eventNameError = eventNameError;
        this.eventTimeError = eventTimeError;
        this.eventDateError = eventDateError;
        this.eventAttendeeError = eventAttendeeError;
        this.isDataValid = false;
    }

    public FormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.emailError = null;
        this.eventDateError = null;
        this.eventNameError = null;
        this.eventTimeError = null;
        this.isDataValid = isDataValid;
    }

    public FormState(@Nullable Integer emailError) {
        this.emailError = emailError;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
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
    public Integer getEventTimeError() {
        return eventTimeError;
    }

    @Nullable
    public Integer getEventAttendeeError() {
        return eventAttendeeError;
    }
}
