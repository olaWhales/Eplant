    package com.whales.eplant.SecurityConfig;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import io.jsonwebtoken.security.Keys;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;

    import java.security.Key;
    import java.util.Base64;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.function.Function;

    @Component
    @Slf4j
    public class JwtUtil {

        private final Key SECRET_KEY;

        public JwtUtil(@Value("${jwt.secret}") String secretKey) {
            // Decode the Base64-encoded secret key from application.properties
            this.SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
        }

        public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        public Boolean validateToken(String token, String username) {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        }

        private Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        private Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        public String generateToken(String username) {
            Map<String, Object> claims = new HashMap<>();
            String token = createToken(claims, username);
            log.info("Generated JWT token for user {}: {}", username, token);
            return token;
        }

        private String createToken(Map<String, Object> claims, String subject) {
            long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                    .compact();
        }
    }