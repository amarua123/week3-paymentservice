package com.accolite.paymentservice.controller;

import com.accolite.paymentservice.config.JwtTokenProvider;
import com.accolite.paymentservice.model.User;
import com.accolite.paymentservice.requestbody.AuthRequestBody;
import com.accolite.paymentservice.requestbody.RegistrationRequestBody;
import com.accolite.paymentservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequestBody body) {
        User user = userService.registerUser(body);
//        System.out.println(jwtTokenProvider.decodeToken(user.getUserSecret()).getSubject());
        return ResponseEntity.ok("User registered. UserSecret: " + user.getToken());
    }

    @PostMapping("/deposit")
    public @ResponseBody String deposit(@AuthenticationPrincipal() UserDetails userDetails, @RequestParam double amount){
        String userName = userDetails.getUsername();
        return userService.deposit(userName, BigDecimal.valueOf(amount));
    }
    @PostMapping("/refreshToken")
    public @ResponseBody String refreshToken(@RequestBody AuthRequestBody body){
        return userService.refreshToken(body);
    }
}
