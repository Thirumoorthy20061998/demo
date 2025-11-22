package com.example.demo.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.PasswordResetRequest;
import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.entity.User;
import com.example.demo.service.PincodeService;
import com.example.demo.service.UserService;


import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

@Autowired
    private  UserService userService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
   @Autowired 
    private  AuthenticationManager authenticationManager;
   @Autowired
    private  PincodeService pincodeService;

  
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        if (userService.userExists(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken!");
        }

       
        Map<String, String> location = pincodeService.getCityAndCountry(request.getPincode());

        if (location == null) {
            return ResponseEntity.badRequest().body("Invalid Pin Code or location service unavailable.");
        }
        
        
        User newUser = new User(
                null, // 
                request.getUsername(), 
                request.getEmail(), 
                passwordEncoder.encode(request.getPassword()), 
                request.getPhoneNumber(), 
                request.getAddress(), 
                request.getPincode(), 
                location.get("city"), 
                location.get("country"), 
                null, 
                null  
            );
        // ------------------------------------------------------------------

        userService.saveUser(newUser);
        return ResponseEntity.ok("User registered successfully! City: " + newUser.getCity() + ", Country: " + newUser.getCountry());
    }

 
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("User logged in successfully!");
    }



   
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> emailRequest) {
        String email = emailRequest.get("email");
        String token = userService.createPasswordResetToken(email);

        if (token == null) {
            return ResponseEntity.ok("If an account exists for " + email + ", a password reset link has been sent.");
        }

        return ResponseEntity.ok("Password reset token generated and emailed. Token: " + token);
    }

    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        User user = userService.findByResetToken(request.getToken())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid or expired password reset token.");
        }

        if (request.getNewPassword() == null || request.getNewPassword().length() < 6) {
             return ResponseEntity.badRequest().body("New password is too short.");
        }

        userService.resetPassword(user, request.getNewPassword());
        return ResponseEntity.ok("Password has been successfully reset.");
    }










}