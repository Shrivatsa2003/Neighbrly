package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.dto.ProfileUpdateRequestDto;
import com.projects.Neighbrly.Neighbrly.dto.UserDto;
import com.projects.Neighbrly.Neighbrly.entity.User;
import com.projects.Neighbrly.Neighbrly.security.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User getUserById(Long userId);
    UserDetails loadUserByUsername(String username);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
