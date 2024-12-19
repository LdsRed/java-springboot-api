package com.jlarcher.supermarketapi.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class SucessResponse<T> {
    private T data;
    private LocalDateTime timeStamp;


    public SucessResponse(T data){
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }
}
