package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents") 
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String incidentId;

    private String entityType;
    private String reporterName;
    private String reporterEmail;
    private String reporterPhoneNumber;

    @Column(columnDefinition = "TEXT")
    private String incidentDetails;

    private LocalDateTime reportedDateTime;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "reporter_user_id", nullable = false)
    private User reportedBy;

    // --- Inner Enums ---
    public enum Priority { HIGH, MEDIUM, LOW }
    public enum Status { OPEN, IN_PROGRESS, CLOSED }

    // --- Constructors ---

    // NoArgsConstructor (required by JPA/Hibernate)
    public Incident() {
    }

    // AllArgsConstructor
    public Incident(Long id, String incidentId, String entityType, String reporterName, String reporterEmail, String reporterPhoneNumber, String incidentDetails, LocalDateTime reportedDateTime, Priority priority, Status status, User reportedBy) {
        this.id = id;
        this.incidentId = incidentId;
        this.entityType = entityType;
        this.reporterName = reporterName;
        this.reporterEmail = reporterEmail;
        this.reporterPhoneNumber = reporterPhoneNumber;
        this.incidentDetails = incidentDetails;
        this.reportedDateTime = reportedDateTime;
        this.priority = priority;
        this.status = status;
        this.reportedBy = reportedBy;
    }

 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    public String getReporterPhoneNumber() {
        return reporterPhoneNumber;
    }

    public void setReporterPhoneNumber(String reporterPhoneNumber) {
        this.reporterPhoneNumber = reporterPhoneNumber;
    }

    public String getIncidentDetails() {
        return incidentDetails;
    }

    public void setIncidentDetails(String incidentDetails) {
        this.incidentDetails = incidentDetails;
    }

    public LocalDateTime getReportedDateTime() {
        return reportedDateTime;
    }

    public void setReportedDateTime(LocalDateTime reportedDateTime) {
        this.reportedDateTime = reportedDateTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // This setter is the one that was previously reported as missing
    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }
}