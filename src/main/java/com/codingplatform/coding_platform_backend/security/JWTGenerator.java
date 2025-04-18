package com.codingplatform.coding_platform_backend.security;

import com.codingplatform.coding_platform_backend.models.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JWTGenerator {

    public String generateToken(Authentication authentication){
        UserDetails user = (UserDetails)  authentication.getPrincipal();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstant.JWT_EXPIRATION);

        UserEntity domainUser = ((CustomUserPrincipal) user).getDomainUser();

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        String token = Jwts.builder()
                .setSubject(domainUser.getEmail())
                .claim("userId", domainUser.getId())
                .claim("name", domainUser.getName())
                .claim("roles", roles)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SecurityConstant.KEY)
                .compact();

        return token;
    }

    public String getEmailFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstant.KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(SecurityConstant.KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception exception){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
