package com.servifacil.SF_BackEnd.dto;

public class AssessmentResponse {

    private int assessmentId;
    private int serviceId;
    private int clientId;
    private int score;
    private String comment;

    public AssessmentResponse(){}

    public AssessmentResponse(int assessmentId, int serviceId, int clientId, int score, String comment) {
        this.assessmentId = assessmentId;
        this.serviceId = serviceId;
        this.clientId = clientId;
        this.score = score;
        this.comment = comment;
    }

    public int getAssessmentId() { return assessmentId; }
    public int getServiceId() { return serviceId; }
    public int getClientId() { return clientId; }
    public int getScore() { return score; }
    public String getComment() { return comment; }

    public void setAssessmentId(int assessmentId) { this.assessmentId = assessmentId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public void setScore(int score) { this.score = score; }
    public void setComment(String comment) { this.comment = comment; }
}
