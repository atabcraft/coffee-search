package com.example.search.coffee.rest;

import com.example.search.coffee.domain.Authority;
import com.example.search.coffee.domain.User;
import com.example.search.coffee.repository.UserRepository;
import com.example.search.coffee.security.JwtDTO;
import com.example.search.coffee.security.JwtTokenProvider;
import com.example.search.coffee.security.UserDTO;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for User, for simplicity sake authentication endpoint is being
 * mapped and performed in this controller.
 *
 * @author Armin
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResource(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/users/sign-up")
    public ResponseEntity<User> signUpUser(@RequestBody UserDTO userDTO) {

        log.info("Signing up user {}", userDTO );
        
        if (userDTO.getEmail() == null
                || userDTO.getPassword() == null || userDTO.getUsername() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userRepository.findByUsername(userDTO.getUsername());
        if( optionalUser.isPresent() ) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        //this is why I used DTO because of security reasons, someone could easily put ROLE_ADMIN try to guess admin role
        // Spring Security 4 by default uses "ROLE_" prefix and I'm fully aware of that
        if (userDTO.getAuthorities().stream().anyMatch(authority -> authority.getName().equals("ROLE_ADMIN"))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    
        user.setRoles(new HashSet<>(userDTO.getAuthorities()));       
        User result = userRepository.save(user);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/users/sign-in")
    public ResponseEntity<JwtDTO> signin(@RequestBody UserDTO user, @RequestParam Boolean rememberMe) {
        try {
            String username = user.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
            String token = jwtTokenProvider.createToken(
                    username,
                    this.userRepository.findByUsername(username).orElseThrow(
                            () -> new UsernameNotFoundException("Username " + username + "not found"))
                            .getRoles()
                            .stream()
                            .map(auth -> auth.getName()).collect(Collectors.toList()),
                    rememberMe);
            JwtDTO jwtDTO = new JwtDTO();
            jwtDTO.setUsername(username);
            jwtDTO.setToken(token);
            return new ResponseEntity<>(jwtDTO, HttpStatus.CREATED);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @GetMapping("/users/current")
    public ResponseEntity<?> getCurrentUser() {
        log.info("request to get user details:");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
       
        UserDTO userDto = new UserDTO();
        userDto.setUsername(userDetails.getUsername());
        if( userDetails.getUsername() == null  ){
            log.info("User not registered");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
        Optional<User> possibleUser = userRepository.findByUsername(userDetails.getUsername());
        if ( possibleUser.isPresent() ) {
            User user = possibleUser.get();
            userDto.setEmail( user.getEmail() );
            userDto.setAuthorities(new ArrayList(user.getAuthorities()));
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("request to get user details: {}", userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
