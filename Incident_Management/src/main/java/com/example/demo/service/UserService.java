package com.example.demo.service;




import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }




 

    public String createPasswordResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiryDate(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        return token;
    }

    public Optional<User> findByResetToken(String token) {
        return userRepository.findAll().stream()
                .filter(u -> token.equals(u.getResetToken()) &&
                             u.getResetTokenExpiryDate() != null &&
                             u.getResetTokenExpiryDate().isAfter(LocalDateTime.now()))
                .findFirst();
    }

    public void resetPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiryDate(null);
        userRepository.save(user);
    }










}