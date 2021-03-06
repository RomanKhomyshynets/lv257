package com.softserve.edu.Resources.util;

public class RegistrationConfirmMail extends Mail {
    String host;
    String token;
    String from;
    String userId;

    public RegistrationConfirmMail(String receiver){
        super(receiver,
                "Registration Confirmation",
                "registrationConfirmation.vm");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
