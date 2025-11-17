package com.example.demo.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.IncidentUpdateRequest;
import com.example.demo.entity.Incident;
import com.example.demo.service.IncidentService;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {
    @Autowired
    private  IncidentService incidentService;

   
    @PostMapping
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
        Incident createdIncident = incidentService.createIncident(incident);
        return new ResponseEntity<>(createdIncident, HttpStatus.CREATED);
    }

   
    @GetMapping
    public ResponseEntity<List<Incident>> getMyIncidents() {
        return ResponseEntity.ok(incidentService.viewMyIncidents());
    }

    
    @GetMapping("/{incidentId}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable String incidentId) {
        return ResponseEntity.ok(incidentService.searchIncident(incidentId));
    }

   
    @PutMapping("/{incidentId}")
    public ResponseEntity<Incident> updateIncident(@PathVariable String incidentId, @RequestBody IncidentUpdateRequest details) {
        Incident updatedIncident = incidentService.updateIncident(incidentId, details);
        return ResponseEntity.ok(updatedIncident);
    }
}