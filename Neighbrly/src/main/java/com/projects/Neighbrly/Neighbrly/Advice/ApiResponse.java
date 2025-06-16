package com.projects.Neighbrly.Neighbrly.Advice;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor

public class ApiResponse<T> {

    private LocalDateTime localDateTime;
    private T data;
    private ApiError apiError;

    public ApiResponse(){
        this.localDateTime = LocalDateTime.now();
    }
    public ApiResponse(T data){
        this();
        this.data = data;
    }
    public ApiResponse(ApiError apiError){
        this();
        this.apiError=apiError;
    }

}
