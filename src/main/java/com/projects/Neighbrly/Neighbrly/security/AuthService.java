package com.projects.Neighbrly.Neighbrly.security;
import com.projects.Neighbrly.Neighbrly.Exception.ResourceNotFoundException;
import com.projects.Neighbrly.Neighbrly.dto.LoginDto;
import com.projects.Neighbrly.Neighbrly.dto.SignupDto;
import com.projects.Neighbrly.Neighbrly.dto.UserDto;
import com.projects.Neighbrly.Neighbrly.entity.User;
import com.projects.Neighbrly.Neighbrly.entity.enums.Role;
import com.projects.Neighbrly.Neighbrly.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final SessionService sessionService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserDto signUp(SignupDto signupDto){
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user!= null){
            throw new RuntimeException("user with this email Id already present");
        }

        User newUser = modelMapper.map(signupDto,User.class);
        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        newUser = userRepository.save(newUser);

        return modelMapper.map(newUser,UserDto.class);

    }
    public String[] login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()
        ));

        User user = (User) authentication.getPrincipal();

        String[] arr = new String[2];
        arr[0] = jwtService.generateAccessToken(user);
        arr[1] = jwtService.generateRefreshToken(user);

        return arr;
    }

    public String refreshToken(String refreshToken) {
        Long id = jwtService.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));
        return jwtService.generateAccessToken(user);
    }


}



