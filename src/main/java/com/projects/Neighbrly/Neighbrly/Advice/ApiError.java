package com.projects.Neighbrly.Neighbrly.Advice;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> subErrors;

}
