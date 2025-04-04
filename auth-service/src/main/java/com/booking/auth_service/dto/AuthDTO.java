package com.booking.auth_service.dto;


import lombok.Data;


public class AuthDTO {
    private String username;
    private String password;
    private Boolean staySign;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStaySign() {
        return staySign;
    }

    public void setStaySign(Boolean staySign) {
        this.staySign = staySign;
    }
}
