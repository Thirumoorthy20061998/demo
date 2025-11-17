package com.example.demo.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;
    private String address;
    private String pinCode;
    private String city;
    private String country;

    private String resetToken;
    private LocalDateTime resetTokenExpiryDate;
    

    public User() {}

   
    public User(Long id, String username, String email, String password, String phoneNumber, String address, String pinCode, String city, String country, String resetToken, LocalDateTime resetTokenExpiryDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.pinCode = pinCode;
        this.city = city;
        this.country = country;
        this.resetToken = resetToken;
        this.resetTokenExpiryDate = resetTokenExpiryDate;
    }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    public LocalDateTime getResetTokenExpiryDate() { return resetTokenExpiryDate; }
    public void setResetTokenExpiryDate(LocalDateTime resetTokenExpiryDate) { this.resetTokenExpiryDate = resetTokenExpiryDate; }

   
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
  
    @Override public boolean isEnabled() { return true; }
}



