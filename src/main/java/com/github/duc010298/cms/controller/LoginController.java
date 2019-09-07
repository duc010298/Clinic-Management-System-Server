package com.github.duc010298.cms.controller;

import com.github.duc010298.cms.dto.UserLoginDTO;
import com.github.duc010298.cms.entity.AppUserEntity;
import com.github.duc010298.cms.repository.AppUserRepository;
import com.github.duc010298.cms.services.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private AppUserRepository appUserRepository;
    private TokenAuthenticationService tokenService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, AppUserRepository appUserRepository, TokenAuthenticationService tokenService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("username") String userName, @RequestParam("password") String password) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (isAuthenticated(authentication)) {
                AppUserEntity appUserEntity = appUserRepository.findByUserName(userName);
                UserLoginDTO userLoginDTO = new UserLoginDTO();
                userLoginDTO.setId(Integer.toString(appUserEntity.getId()));
                userLoginDTO.setFullName(appUserEntity.getFullName());
                userLoginDTO.setToken(tokenService.getToken(userName));
                return ResponseEntity.status(HttpStatus.OK).body(userLoginDTO);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
