package org.project.pals.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "fljh8hro823hf8u3o74hf8odh8u2ho78whfiuhwe8ofh8o7sdioufh8734h8o7hf8ouh4278hf8o7h7o3gf76owh238oydg76q23hdg76g7do6";

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(key).build();
        Date expirationDate = parser
                .parseClaimsJws(token)
                .getBody().getExpiration();
        return expirationDate.before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


}
