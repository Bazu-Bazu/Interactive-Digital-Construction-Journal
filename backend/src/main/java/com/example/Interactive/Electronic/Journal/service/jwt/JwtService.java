package com.example.Interactive.Electronic.Journal.service.jwt;

import com.example.Interactive.Electronic.Journal.dto.CustomUserDetails;
import com.example.Interactive.Electronic.Journal.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Getter
public class JwtService {

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("authorities", authorities);

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            claims.put("userId", customUserDetails.getUser().getId());
            claims.put("role", customUserDetails.getUser().getRole());
            claims.put("accessibleObject", customUserDetails.getUser().getObjectId());
        }

        Date date = createExpirationDate(accessTokenExpiration);

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Date date = createExpirationDate(refreshTokenExpiration);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private Date createExpirationDate(long tokenExpiration) {
        return Date.from(
                Instant.now()
                        .plusMillis(tokenExpiration)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public Long getAccessibleObjectId(String token) {
        Claims claims = extractAllClaims(token);

        return claims.get("accessibleObject", Long.class);
    }

    public Role getUserRole(String token) {
        Claims claims = extractAllClaims(token);

        return claims.get("role", Role.class);
    }

    public Long getUserId(String token) {
        Claims claims = extractAllClaims(token);

        return claims.get("userId", Long.class);
    }

    private SecretKey getSignInKey() {
        byte[] keyBites = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBites);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);

        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");
        if (authorities == null) {
            return List.of();
        }

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}