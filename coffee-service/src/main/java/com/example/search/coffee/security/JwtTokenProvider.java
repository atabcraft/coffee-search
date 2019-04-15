package com.example.search.coffee.security;

import com.example.search.coffee.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * This class was inspired by https://github.com/hantsy/springboot-jwt-sample
 * I added remember me functionality
 * @author Armin
 */
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key.secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    @Value("${security.jwt.token.remember-me-expire-length}")
    private long rememberMevalidityInMilliseconds;


    private static final String ROLES_FOR_CLAIMS = "roles";

    public JwtTokenProvider() {
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<String> roles, boolean isRememberMe) {

        Claims claims = Jwts.claims().setSubject(username);
        String rolesForClaims = roles.stream()
            .collect(Collectors.joining(","));
        claims.put(ROLES_FOR_CLAIMS, rolesForClaims);

        Date now = new Date();

        Date validity = isRememberMe
                ? new Date(now.getTime() + rememberMevalidityInMilliseconds)
                : new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(ROLES_FOR_CLAIMS).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User user = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user, "", authorities);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

}
