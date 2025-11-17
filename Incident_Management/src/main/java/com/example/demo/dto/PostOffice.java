package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PostOffice {
    private String District; 
    private String Country;
    
    public PostOffice() {
    }

    
    public PostOffice(String District,String Country) {
        this.District = District;
        
        this.Country = Country;
    }

    

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String District) {
        this.District = District;
    }

    
    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }
}