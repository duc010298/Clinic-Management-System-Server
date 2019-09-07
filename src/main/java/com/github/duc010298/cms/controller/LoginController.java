package com.github.duc010298.cms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @PostMapping
    public String login(@RequestParam("username") String userName, @RequestParam("password") String password) {
        System.out.println(userName);
        return null;
    }
}
