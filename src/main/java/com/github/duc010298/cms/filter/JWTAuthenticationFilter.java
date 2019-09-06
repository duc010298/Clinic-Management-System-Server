package com.github.duc010298.cms.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.duc010298.cms.entity.AppUserEntity;
import com.github.duc010298.cms.repository.AppRoleRepository;
import com.github.duc010298.cms.repository.AppUserRepository;
import com.github.duc010298.cms.services.TokenAuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private TokenAuthenticationService tokenAuthenticationService;
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    public JWTAuthenticationFilter(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String userName = tokenAuthenticationService.getUserNameFromRequest((HttpServletRequest) request);

        AppUserEntity userInfoOnDB = appUserRepository.findByUserName(userName);
        if (userInfoOnDB == null) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
            chain.doFilter(request, response);
            return;
        }
        Date issueAt = tokenAuthenticationService.getIssueAtFromRequest((HttpServletRequest) request);
        if (userInfoOnDB.getTokenActiveAfter().before(issueAt)) {
            List<String> roleNames = this.appRoleRepository.getRoleNames(userInfoOnDB.getId());
            List<GrantedAuthority> grantList = new ArrayList<>();
            if (roleNames != null) {
                for (String role : roleNames) {
                    GrantedAuthority authority = new SimpleGrantedAuthority(role);
                    grantList.add(authority);
                }
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, grantList);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
