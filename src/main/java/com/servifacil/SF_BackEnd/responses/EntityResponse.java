package com.servifacil.SF_BackEnd.responses;

import java.time.LocalDateTime;

public class EntityResponse<T> {

    private boolean sucess;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public EntityResponse(boolean sucess, String message, T data){
        this.sucess = sucess;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSucess(){ return sucess; }

    public String getMessage(){ return message; }

    public T getData(){ return data; }

    public LocalDateTime getTimestamp(){ return timestamp; }
}
