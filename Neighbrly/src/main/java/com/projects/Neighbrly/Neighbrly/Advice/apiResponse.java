package com.projects.Neighbrly.Neighbrly.Advice;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class apiResponse<T> {

    private LocalDateTime localDateTime;
    private T data;
    private ApiError apiError;

    public apiResponse(){
        this.localDateTime = LocalDateTime.now();
    }
    public apiResponse(T data){
        this();
        this.data = data;
    }
    public apiResponse(ApiError apiError){
        this();
        this.apiError=apiError;
    }

}
