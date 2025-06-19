package com.projects.Neighbrly.Neighbrly.dto;

import com.projects.Neighbrly.Neighbrly.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDto {
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
}
