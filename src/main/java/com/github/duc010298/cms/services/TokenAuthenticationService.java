package com.github.duc010298.cms.services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationService {

    private final String SECRET = "01021998";

    private final String TOKEN_PREFIX = "Bearer";

    private final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse res, String username) {
        String JWT = Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
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

    public Date getIssueAtFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        try {
            if (token != null) {
                Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
                return claims.getIssuedAt();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}