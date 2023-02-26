package com.example.ElearningAndExamJNPT.security.jwt;


import com.example.ElearningAndExamJNPT.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "TanDung";
    private int jwtExpiration = 86400;
    public String createToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT Signature: ", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT format token: ", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token: ", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token: ", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT Claims string is empty: ", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
                .getBody().getSubject();
    }
}
