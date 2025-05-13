package com.projects.Neighbrly.Neighbrly.controller;

import com.projects.Neighbrly.Neighbrly.dto.LoginDto;
import com.projects.Neighbrly.Neighbrly.dto.SignupDto;
import com.projects.Neighbrly.Neighbrly.dto.UserDto;
import com.projects.Neighbrly.Neighbrly.security.AuthService;
import com.projects.Neighbrly.Neighbrly.security.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupDto signupDto){
        UserDto userDto = userService.signUp(signupDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response){

        String token = authService.login(loginDto);
        Cookie cookie = new Cookie("token",token);
        response.addCookie(cookie);

        return ResponseEntity.ok(token);
    }
 }
