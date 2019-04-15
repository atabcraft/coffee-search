package com.example.search.coffee.service;


import com.example.search.coffee.repository.UserRepository;
import com.example.search.coffee.security.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Using UserDetailsService loadUserByUsername for user %", username);
        return this.userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }
}