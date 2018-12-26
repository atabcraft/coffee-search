package com.example.search.coffee.config;

import com.example.search.coffee.security.JwtConfigurer;
import com.example.search.coffee.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    
    
    public SecurityConfig( JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("configuring app!");
        http
            .cors().and()
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/api/users/sign-in").permitAll()
                .antMatchers("/api/users/sign-up").permitAll()
                //implicitly that means ROLE_BAD_MANAGER can't get coffe
                .antMatchers(HttpMethod.GET, "/api/coffee/**").hasAnyRole("ADMIN","ENGINEER")
                .antMatchers(HttpMethod.POST, "/api/coffee/**").hasAnyRole("ADMIN","ENGINEER")
                .antMatchers(HttpMethod.PUT, "/api/coffee/**").hasAnyRole("ADMIN","BAD_MANAGER")
                //BAD_MANAGER can try
                .antMatchers(HttpMethod.DELETE, "/coffee/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .apply(new JwtConfigurer(jwtTokenProvider));
    }
}