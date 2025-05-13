package com.projects.Neighbrly.Neighbrly.security;

import com.projects.Neighbrly.Neighbrly.Exception.resourceNotFound;
import com.projects.Neighbrly.Neighbrly.dto.LoginDto;
import com.projects.Neighbrly.Neighbrly.dto.SignupDto;
import com.projects.Neighbrly.Neighbrly.dto.UserDto;
import com.projects.Neighbrly.Neighbrly.entity.User;
import com.projects.Neighbrly.Neighbrly.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private  final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new BadCredentialsException("User with this email does not exists"+username));

    }

    public User getUserById(Long userId ){
        return userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User with this userId does not exists"+userId));
    }



    public UserDto signUp(SignupDto signupDto){

        Optional<User> user = userRepository.findByEmail(signupDto.getEmail());
        if(user.isPresent()){
            throw  new BadCredentialsException("User with this email already exists"+signupDto.getEmail());
        }

        User toBeCreatedUser = modelMapper.map(signupDto,User.class);

        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
       User createdUser =  userRepository.save(toBeCreatedUser);

       return modelMapper.map(createdUser,UserDto.class);


    }


}