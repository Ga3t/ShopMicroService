package com.booking.auth_service.config;


import com.booking.auth_service.security.JwtAuthEntryPoint;
import com.booking.auth_service.security.JwtAuthenticationFilter;
import com.booking.auth_service.services.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private JwtAuthEntryPoint authEntryPoint;

    private UsersServiceImpl usersService;

    @Autowired
    public SecurityConfig(JwtAuthEntryPoint authEntryPoint, UsersServiceImpl usersService) {
        super();
        this.authEntryPoint = authEntryPoint;
        this.usersService = usersService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity https) throws Exception{
        https
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exeption -> exeption
                        .authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/authentication/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(AbstractHttpConfigurer::disable);
        https.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return https.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configurationSource = new CorsConfiguration();
        configurationSource.addAllowedOrigin("*");
        configurationSource.setAllowedMethods(Arrays.asList("GET", "POST"));
        configurationSource.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configurationSource.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configurationSource);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthFilter(){
        return new JwtAuthenticationFilter();
    }
}
