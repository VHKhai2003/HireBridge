package com.vhkhai.security.jwt;

import com.vhkhai.port.auth.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils implements Jwt {
    private final String jwtSecret = "your-secret-keykakjeoodjjdjjdnviqquhdl84220002jrjjoidoso938aunvnaoidj";
    private final long jwtAccessExpirationMs = 60 * 60 * 1000;
    private final long jwtRefreshExpirationMs = 30L * 24 * 60 * 60 * 1000;

//    public String generateToken(Account account, String type) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("role", account.getType());
//        claims.put("accountId", account.getId());
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(account.getEmail())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtAccessExpirationMs))
//                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
//                .compact();
//    }

//    public Account getAccountFromToken(String token) {
//        Map<String, Object> claims = Jwts.parserBuilder()
//                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return Account.builder()
//                .id(UUID.fromString(claims.get("accountId").toString()))
//                .email(claims.get("sub").toString())
//                .type(AccountType.valueOf(claims.get("role").toString()))
//                .build();
//    }

    @Override
    public String generateAccessToken(UUID id) {
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtAccessExpirationMs))
                .setIssuer("vhkhai@gmail.com")
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(UUID id) {
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
                .setIssuer("vhkhai@gmail.com")
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public UUID getAccountIdFromToken(String token) {
        String accountId = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return UUID.fromString(accountId);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
