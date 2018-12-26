package com.example.search.coffee.security;

import com.example.search.coffee.rest.UserResource;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter responsible for the authentication process, filter which will intercept HTTP requests.
 * @author Armin
 */
public class JwtTokenFilter extends OncePerRequestFilter {

   private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class); 
    
   private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
      
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws IOException, ServletException  {
        String token = jwtTokenProvider.resolveToken(req);
        //please note here because token can be null when requesting 
        // over HTTP on endpoints /api/users/sign-in or /api/users/sign-up
        //first token != null is necessery
        log.info("resolved token as: {}", token);
        if ( token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
            log.info("Authorities resolved as {}", auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        fc.doFilter(req, res);
    }
}
