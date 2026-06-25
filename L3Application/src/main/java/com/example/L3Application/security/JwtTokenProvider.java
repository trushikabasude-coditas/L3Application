package com.example.L3Application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Component
public class JwtTokenProvider implements TokenProvider {
    private final SecretKey key;
    private final Long expiration;
  public JwtTokenProvider(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.access-expiration-ms}")long expiration) {
        this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
this.expiration=expiration;
    }
    @Override
    public String generateAccessToken(String username,String role) {
        Date now=new Date();
        return Jwts.builder()
                .subject(username)
                .claim("role",role)
                .issuedAt(now)
                .expiration(new Date(now.getTime()+expiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();}
    @Override
 public String extractUsername(String token){
      return parse(token).getSubject();
    } @Override
    public boolean isTokenValid(String token, UserDetails userDetails){
        try {
            Claims claims=parse(token);
            return claims.getSubject().equals(userDetails.getUsername())
                    && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;}
    }
    private Claims parse(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
