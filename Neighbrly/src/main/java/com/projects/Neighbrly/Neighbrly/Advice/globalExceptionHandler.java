package com.projects.Neighbrly.Neighbrly.Advice;

import com.projects.Neighbrly.Neighbrly.Exception.resourceNotFound;
import io.jsonwebtoken.JwtException;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Data
@Builder
@RestControllerAdvice
@RequiredArgsConstructor
public class globalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<apiResponse<?>> handleAuthenticationException(AuthenticationException ex){
            ApiError apiError = ApiError.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message(ex.getLocalizedMessage())
                    .build();
            return buildErrorResponseEntity(apiError);
        }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<apiResponse<?>> handleJwtException(JwtException ex){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getLocalizedMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }



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


