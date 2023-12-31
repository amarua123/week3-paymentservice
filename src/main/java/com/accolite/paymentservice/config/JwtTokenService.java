package com.accolite.paymentservice.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
@Service
public class JwtTokenService {

    @Autowired
    public JwtTokenService() {

    }

    // Secret key for JWT signature verification.
    @Value("${app.jwt.expiration}")
    private Integer jwtExpirationInMs;
    @Value("${app.jwt.secret}")
    private String KEY;

    /**
     * Get Signing key for validating JWTs.
     * @return The signing key
     */
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
    }

    /**
     * Extract username from the JWT token.
     * @param token the JWT token to extract the username from.
     * @return the username from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiry date from the JWT token.
     * @param token the JWT token to extract the expiry date from.
     * @return the expiry date from the token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Validate a JWT token against a username & expiry date.
     * @param token the JWT token to be validated.
     * @param userDetails the user details to check against the token/
     * @return true if the token is valid for the provided user, otherwise false.
     */
    public boolean validToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !tokenExpired(token);
    }

    /**
     * Check if a JWT token has expired
     * @param token the JWT token to be checked.
     * @return True if the token is expired, otherwise false.
     */
    private boolean tokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Wrapper function for extracting a claim from a JWT token.
     * @param token The JWT token to extract the claim.
     * @param resolveClaim A function to resolve the claim from the token.
     * @param <T> The type of the claim to be extracted.
     * @return an extracted claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> resolveClaim) {
        final Claims claims = extractAllClaims(token);
        return resolveClaim.apply(claims);
    }

    /**
     * Extract all claims from a JWT token
     * @param token the JWT token to extract all claims from.
     * @return the claims from the JWT token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(KEY)
                .build().parseSignedClaims(token).getPayload();
    }

    /**
     * Generates a JWT token for a user using default claims.
     * @param userDetails The user details to generate a token from.
     * @return The generated JWT token as a string.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token for a user with custom claims.
     * @param extractClaims Custom claims to be included in the JWT.
     * @param userDetails The user details to generate a token from.
     * @return The generated JWT token as a string.
     */
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extractClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)) // set expiry date to next day
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !tokenExpired(token));
    }
}

