package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.entity.User;
import com.projects.Neighbrly.Neighbrly.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElse(null);
    }


}
