package com.github.duc010298.cms.services;

import com.github.duc010298.cms.configuration.ApiConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenAuthenticationService {

    private ApiConfig apiConfig;

    public TokenAuthenticationService(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public String getToken(String username) {
        return Jwts.builder().setSubject(username).signWith(SignatureAlgorithm.HS512, apiConfig.getTokenSecretKey()).compact();
    }

    public String getUserNameFromRequest(HttpServletRequest request) {
        String token = request.getHeader(apiConfig.getTokenHeaderString());
        try {
            if (token != null) {
                Claims claims = Jwts.parser().setSigningKey(apiConfig.getTokenSecretKey()).parseClaimsJws(token.replace(apiConfig.getTokenPrefix(), "")).getBody();
                return claims.getSubject();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
