package com.booking.auth_service.services;


import com.booking.auth_service.dto.AuthDTO;
import com.booking.auth_service.dto.RegistryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String LogInUser(AuthDTO authDTO);
    Boolean RegistryUser(RegistryDTO registryDTO);
}
