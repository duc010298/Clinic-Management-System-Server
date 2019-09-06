package com.github.duc010298.cms.filter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.duc010298.cms.services.TokenAuthenticationService;
import com.github.duc010298.cms.services.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private TokenAuthenticationService tokenAuthenticationService;
    private UserDetailsServiceImpl userDetailsService;

    public JWTLoginFilter(String url, AuthenticationManager authManager,TokenAuthenticationService tokenAuthenticationService,
                          UserDetailsServiceImpl userDetailsService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDetails currentUser = userDetailsService.loadUserByUsername(username);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(currentUser, password, currentUser.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        tokenAuthenticationService.addAuthentication(response, authResult.getName());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}