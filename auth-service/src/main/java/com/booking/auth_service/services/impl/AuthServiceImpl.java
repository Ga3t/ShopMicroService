package com.booking.auth_service.services.impl;

import com.booking.auth_service.dto.AuthDTO;
import com.booking.auth_service.dto.RegistryDTO;
import com.booking.auth_service.models.UserEntity;
import com.booking.auth_service.repository.UserRepository;
import com.booking.auth_service.security.JwtGeneratorTest;
import com.booking.auth_service.services.AuthService;
import com.booking.core.security.JwtGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


@Service
@Primary
public class AuthServiceImpl implements AuthService {

    private  UserRepository userRepository;
    private  AuthenticationManager authenticationManager;
    private JwtGeneratorTest jwtGenerator;
    private  PasswordEncoder passwordEncoder;

    private static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtGeneratorTest jwtGenerator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String LogInUser(AuthDTO authDTO) {
       System.out.println(authDTO);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDTO.getUsername(),
                        authDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity user = userRepository.findByUsername(authDTO.getUsername())
            .orElseThrow(()-> new RuntimeException("User not found"));

        try{
            String token = jwtGenerator.generateAccessToken(user.getUsername(), user.getId(), user.getRole());
          return token;
        }catch (Exception exception){
            logger.error("Error generating token: ", exception);
            throw new RuntimeException("Failed to generate token", exception);
        }
    }

    @Override
    public Boolean RegistryUser(RegistryDTO registryDTO) {
        UserEntity user = new UserEntity();
        BigDecimal rating = BigDecimal.valueOf(5.0);;
        try{
            user.setName(registryDTO.getName());
            user.setPassword(passwordEncoder.encode(registryDTO.getPassword()));
            user.setUsername(registryDTO.getUsername());
            user.setBirthday(registryDTO.getBirthday());
            user.setEmail(registryDTO.getEmail());
            user.setRating(rating);
            user.setSurname(registryDTO.getSurname());
            user.setRole(registryDTO.getRole());
            user.setPhoneNumber(registryDTO.getPhoneNumber());
            userRepository.save(user);
            return true;

        }catch (Exception exception){
            logger.error("Failed to registered", exception);
            return false;
        }
    }
}
