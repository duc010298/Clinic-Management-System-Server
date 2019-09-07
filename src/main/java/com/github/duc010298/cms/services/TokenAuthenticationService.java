package com.github.duc010298.cms.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenAuthenticationService {

    private final String SECRET = "01021998";

    private final String HEADER_STRING = "Authorization";

    public String getToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public String getUserNameFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        try {
            if (token != null) {
                Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
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
                Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
                return claims.getIssuedAt();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
