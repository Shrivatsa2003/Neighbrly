package com.projects.Neighbrly.Neighbrly.Advice;

import com.projects.Neighbrly.Neighbrly.Exception.resourceNotFound;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Data
@Builder
@RestControllerAdvice
public class globalExceptionHandler {

    @ExceptionHandler(resourceNotFound.class)
    public ResponseEntity<apiResponse<?>> resourceNotFound( resourceNotFound exception){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<apiResponse<?>> internalServerError( Exception exception){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }


    private ResponseEntity<apiResponse<?>> buildErrorResponseEntity(ApiError apiError){

        return new ResponseEntity<>(new apiResponse<>(apiError),apiError.getStatus());
    }
}

