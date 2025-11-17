package com.example.demo.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.IncidentUpdateRequest;
import com.example.demo.entity.Incident;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IncidentRepository;
import com.example.demo.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class IncidentService {
    @Autowired
    private IncidentRepository incidentRepository;
    @Autowired
    private UserRepository userRepository;

    private String generateUniqueIncidentId() {
        String newId;
        Random random = new Random();
        do {
            int randomNum = random.nextInt(90000) + 10000; // 5-digit number
            int currentYear = LocalDate.now().getYear();
            newId = "RMG" + randomNum + currentYear;
        } while (incidentRepository.findByIncidentId(newId).isPresent());
        return newId;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    public Incident createIncident(Incident incident) {
        User currentUser = getCurrentUser();
        incident.setReportedBy(currentUser);
        incident.setIncidentId(generateUniqueIncidentId());
        incident.setReportedDateTime(LocalDateTime.now());
        incident.setStatus(Incident.Status.OPEN); // Default status

        // Auto-fill reporter details from logged-in user
        incident.setReporterName(currentUser.getUsername());
        incident.setReporterEmail(currentUser.getEmail());
        incident.setReporterPhoneNumber(currentUser.getPhoneNumber());

        return incidentRepository.save(incident);
    }

    public List<Incident> viewMyIncidents() {
        User currentUser = getCurrentUser();
        return incidentRepository.findByReportedBy(currentUser);
    }

    public Incident searchIncident(String incidentId) {
        User currentUser = getCurrentUser();
        Incident incident = incidentRepository.findByIncidentId(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found: " + incidentId));

        // Ensure the incident belongs to the current user
        if (!incident.getReportedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Incident not found or you are not authorized to view it.");
        }
        return incident;
    }

    public Incident updateIncident(String incidentId, IncidentUpdateRequest details) {
        User currentUser = getCurrentUser();
        Incident existingIncident = incidentRepository.findByIncidentId(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found: " + incidentId));

        // Check ownership
        if (!existingIncident.getReportedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to edit this incident.");
        }

        // Closed incidents are not editable.
        if (existingIncident.getStatus() == Incident.Status.CLOSED) {
            throw new IllegalStateException("Closed incidents cannot be edited.");
        }

        // Update editable fields
        if (details.getIncidentDetails() != null) {
            existingIncident.setIncidentDetails(details.getIncidentDetails());
        }
        if (details.getPriority() != null) {
            existingIncident.setPriority(details.getPriority());
        }
        if (details.getStatus() != null) {
            existingIncident.setStatus(details.getStatus());
        }

        return incidentRepository.save(existingIncident);
    }
}