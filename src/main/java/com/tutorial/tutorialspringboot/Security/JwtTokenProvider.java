package com.tutorial.tutorialspringboot.Security;

import com.tutorial.tutorialspringboot.Exeption.BlogAppException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    //Obtener valor de propiedad
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-exipartion-milli}")
    private long jwtExipartionMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExipartionMs);

        String token = Jwts.builder().setSubject(username).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

        return token;
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "No valid JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Empty JWT token");
        }

        return true;
    }


}
