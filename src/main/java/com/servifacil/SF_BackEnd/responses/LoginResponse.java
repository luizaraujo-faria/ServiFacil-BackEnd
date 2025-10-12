package com.servifacil.SF_BackEnd.responses;

import java.time.LocalDateTime;

public class LoginResponse<T> {

    private boolean sucess;
    private String message;
    private String token;
    private LocalDateTime timestamp;

    public LoginResponse(boolean sucess, String message, String token){
        this.sucess = sucess;
        this.message = message;
        this.token = token;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSucess(){ return sucess; }

    public String getMessage(){ return message; }

    public String getToken(){ return token; }

    public LocalDateTime getTimestamp(){ return timestamp; }
}
