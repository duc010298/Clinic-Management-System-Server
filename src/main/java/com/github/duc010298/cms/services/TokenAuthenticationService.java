package com.github.duc010298.cms.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenAuthenticationService {

    private final String SECRET = "01021998";

    private final String HEADER_STRING = "Authorization";

    private final String TOKEN_PREFIX = "Bearer";

    public String getToken(String username) {
        return Jwts.builder().setSubject(username).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public String getUserNameFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        try {
            if (token != null) {
                Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
                return claims.getSubject();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
