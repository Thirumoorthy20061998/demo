package com.example.demo.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PincodeService {

    private final WebClient webClient;

    public PincodeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.postalpincode.in").build();
    }

    /**
     * Fetches city (District) and country details for a given PIN code reliably.
     */
    public Map<String, String> getCityAndCountry(String pincode) {
        try {
            System.out.println("ENTERING PincodeService");
            
            ParameterizedTypeReference<List<Map<String, Object>>> outerTypeRef =
                new ParameterizedTypeReference<List<Map<String, Object>>>() {};

            List<Map<String, Object>> responses = webClient.get()
                .uri("/pincode/{pincode}", pincode)
                .retrieve()
                .bodyToMono(outerTypeRef)
                .block();

            if (responses != null && !responses.isEmpty()) {
                Map<String, Object> response = responses.get(0);
                String status = (String) response.get("Status");

                if ("Success".equalsIgnoreCase(status)) {
                    
                    @SuppressWarnings("unchecked")
                    List<Map<String, String>> postOffices = 
                        (List<Map<String, String>>) response.get("PostOffice");

                    System.out.println("EXECUTE Success Block");

                    if (postOffices != null && !postOffices.isEmpty()) {
                        Map<String, String> office = postOffices.get(0);
                        Map<String, String> location = new HashMap<>();

                        String district = office.get("District");
                        String country = office.get("Country");

                        if (district != null && country != null) {
                             location.put("city", district);
                             location.put("country", country);
                             return location;
                        }
                    }
                }
                
                System.err.println("Pincode API returned non-success status: " + status);

            } else {
                System.err.println("Pincode API returned empty response.");
            }
            
        } catch (Exception e) {
            System.err.println("Pincode API call failed with exception: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace(); 
        }

        return null;
    }
}