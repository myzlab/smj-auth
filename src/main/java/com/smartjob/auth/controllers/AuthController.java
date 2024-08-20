package com.smartjob.auth.controllers;

import com.smartjob.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity login(
        @RequestHeader(required = false) final String email,
        @RequestHeader(required = false) final String password
    ) {
        return authService.login(email, password);
    }
    
}