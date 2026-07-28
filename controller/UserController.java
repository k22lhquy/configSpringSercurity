package com.last.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile")
    public String profile(Authentication authentication) {
    	System.out.println(authentication.getCredentials());
    	System.out.println(authentication.getDetails());
    	System.out.println(authentication.getName());
    	System.out.println(authentication.getPrincipal());
    	System.out.println(authentication.getAuthorities());
        return authentication.getName();

    }

}
