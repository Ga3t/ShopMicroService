package com.booking.auth_service.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UsersService {

    UserDetails loadUserByUsername(String username);
}
