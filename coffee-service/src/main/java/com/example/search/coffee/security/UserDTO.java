package com.example.search.coffee.security;

import com.example.search.coffee.domain.Authority;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

/**
 * DTO for user.
 * @author Armin
 */
@JsonInclude(Include.NON_NULL)
public class UserDTO {

    public UserDTO(){}
    
    public UserDTO(String username, String password, String firstName, String lastName, String email, List<String> roles, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.authorities = authorities;
    }
    
    private String username;

    private String password;
    
    private String firstName;

    private String lastName;

    private String email;
    
    private List<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
    
    @Override
    public String toString() {
        return "UserDTO{" + "username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", authorities=" + authorities + '}';
    }
    
}
