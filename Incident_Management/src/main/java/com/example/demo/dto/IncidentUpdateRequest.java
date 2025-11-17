package com.example.demo.dto;

import com.example.demo.entity.Incident;

public class IncidentUpdateRequest {
    private String incidentDetails;
   
    private Incident.Priority priority; 
    private Incident.Status status;

   
    public IncidentUpdateRequest() {
    }

   

    public String getIncidentDetails() {
        return incidentDetails;
    }

    public void setIncidentDetails(String incidentDetails) {
        this.incidentDetails = incidentDetails;
    }

    public Incident.Priority getPriority() {
        return priority;
    }

    public void setPriority(Incident.Priority priority) {
        this.priority = priority;
    }

    public Incident.Status getStatus() {
        return status;
    }

    public void setStatus(Incident.Status status) {
        this.status = status;
    }
}