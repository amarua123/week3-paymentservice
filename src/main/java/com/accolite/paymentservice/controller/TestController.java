package com.accolite.paymentservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/authenticate")
    public String authenticate(){
        return "Hi, This is authenticate service";
    }
    @GetMapping("/hello")
    @PreAuthorize("isAuthenticated()")
    public String hello(@AuthenticationPrincipal() UserDetails userDetails){
        String login = userDetails.getUsername();
        System.out.println(login);
        return "Hello " + login  +", how are you ?";
    }
}
