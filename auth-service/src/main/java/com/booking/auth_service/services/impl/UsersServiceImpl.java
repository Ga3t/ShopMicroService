package com.booking.auth_service.services.impl;


import com.booking.auth_service.models.UserEntity;
import com.booking.auth_service.repository.UserRepository;
import com.booking.auth_service.services.UsersService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Primary
public class UsersServiceImpl implements UsersService {

    private UserRepository userRepository;

    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found!"));
        return new User(user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
