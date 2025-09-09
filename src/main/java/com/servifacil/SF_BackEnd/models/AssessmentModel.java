package com.servifacil.SF_BackEnd.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tbAssessments")
public class AssessmentModel {

    @Id
    @Column(name = "Assessment_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assessmentId;

    @Column(name = "Service_ID")
    private int serviceId;

    @Column(name = "Client_ID")
    private int clientId;

    @Column(name = "Score")
    @NotBlank
    private int score;

    @Column(name = "Comments")
    private String comment;

    // GETTERS & SETTERS

    public int getAssessmentId(){ return this.assessmentId; }

    public int getServiceId(){ return this.serviceId; }
    public void setServiceId(int serviceId){ this.serviceId = serviceId; }

    public int getClientId(){ return this.clientId; }
    public void setClientId(int clientId){ this.clientId = clientId; }

    public int getScore(){ return this.score; }
    public void setScore(int score){ this.score = score; }

    public String getComment(){ return this.comment; }
    public void setComment(String comment){ this.comment = comment; }
}
