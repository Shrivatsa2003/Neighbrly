package com.projects.Neighbrly.Neighbrly.Exception;

import org.springframework.http.ResponseEntity;

public class resourceNotFound extends RuntimeException {
    public resourceNotFound(String message){
        super(message);
    }


}
