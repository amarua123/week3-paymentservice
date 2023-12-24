package com.accolite.paymentservice.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
@Component
public class JwtTokenProvider {
    @Value("${app.jwt.expiration}")
    private Integer jwtExpirationInMs;
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    public String createToken(String msg){
//        System.out.println(jwtSecret);
        Date now = new Date();

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(jwtSecret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder().subject(msg).issuedAt(new Date()).expiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }
    public Claims decodeToken(String token){
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(jwtSecret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parser()
                .setSigningKey(signingKey)
                .build().parseSignedClaims(token).getPayload();
    }
}

