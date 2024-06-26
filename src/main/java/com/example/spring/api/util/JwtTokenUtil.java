package com.example.spring.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;
    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims= new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY *1000))
               // .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)),Jwts.SIG.HS256)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String getUserNameFromToken(String jwtToken){
        return getClaimFromToken(jwtToken,Claims::getSubject);
    }
    private <T> T getClaimFromToken(String token, Function<Claims,T> claimsTFunction){
        final Claims claims=Jwts.parser().setSigningKey(secret).build().parseSignedClaims(token).getBody();
        return claimsTFunction.apply(claims);
    }


    public boolean validateToken(String jwtToken, UserDetails userDetails) {

        final String userName=getUserNameFromToken(jwtToken);

        return userName.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        final Date expirationDate=getExpirationDateFromToken(jwtToken);

        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken,Claims::getExpiration);
    }
}
