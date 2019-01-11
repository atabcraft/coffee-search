package com.example.search.coffee.config;


import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
 
public class UsernameAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        
        String principalUsername = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
          principalUsername = ((UserDetails)principal).getUsername();
        } else if( principal == null ){
            principalUsername = null;
        } else {
            principalUsername = principal.toString();
        }
        
        return Optional.ofNullable(principalUsername);
    }
    
 

}