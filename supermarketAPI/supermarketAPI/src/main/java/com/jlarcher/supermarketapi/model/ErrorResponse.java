package com.jlarcher.supermarketapi.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse extends RuntimeException{

    private List<String> errores;
    private String message;
    private LocalDateTime timeStamp;


    public ErrorResponse(){
        this.timeStamp = LocalDateTime.now();
    }
    public ErrorResponse(String message){
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

    public ErrorResponse(List<String> errores, String message){
        this.errores = errores;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

}
