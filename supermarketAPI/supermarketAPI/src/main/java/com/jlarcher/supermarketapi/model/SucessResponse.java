package com.jlarcher.supermarketapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class SucessResponse<T> {
    private T data;
    private LocalDateTime timeStamp;

    @JsonCreator
    public SucessResponse(@JsonProperty("data") T data) { this.data = data; this.timeStamp = LocalDateTime.now(); }

}
