package com.booking.auth_service.controllers;


import com.booking.auth_service.dto.AuthDTO;
import com.booking.auth_service.dto.AuthResponseDto;
import com.booking.auth_service.dto.RegistryDTO;
import com.booking.auth_service.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("authentication")
public class AuthController {
    private AuthService authService;
    private AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login (@RequestBody AuthDTO authDTO){

        String token = authService.LogInUser(authDTO);
        if(token !=null){
            return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new AuthResponseDto("Authentication failed"), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registry (@RequestBody RegistryDTO regDto){
        Boolean register= authService.RegistryUser(regDto);
        if(register)
            return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Error while registration", HttpStatus.BAD_REQUEST);

    }
}
