package com.github.duc010298.cms.configuration;

import com.github.duc010298.cms.filter.JWTAuthenticationFilter;
import com.github.duc010298.cms.filter.JWTLoginFilter;
import com.github.duc010298.cms.repository.AppRoleRepository;
import com.github.duc010298.cms.repository.AppUserRepository;
import com.github.duc010298.cms.services.TokenAuthenticationService;
import com.github.duc010298.cms.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenAuthenticationService tokenAuthenticationService;
    private UserDetailsServiceImpl userDetailsService;
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    @Autowired
    public SpringSecurityConfig(UserDetailsServiceImpl userDetailsService, AppUserRepository appUserRepository,
                                AppRoleRepository appRoleRepository, TokenAuthenticationService tokenAuthenticationService) {
        this.userDetailsService = userDetailsService;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(),
                        tokenAuthenticationService, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(appUserRepository, appRoleRepository,
                        tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}