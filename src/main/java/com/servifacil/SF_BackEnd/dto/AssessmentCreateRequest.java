package com.servifacil.SF_BackEnd.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AssessmentCreateRequest {

    @Positive(message = "serviceId deve ser > 0")
    private int serviceId;

    @Positive(message = "clientId deve ser > 0")
    private int clientId;

    @Min(value = 1, message = "Score deve ser entre 1 e 5")
    @Max(value = 5, message = "Score deve ser entre 1 e 5")
    private int score;

    @Size(max = 500, message ="Comentário deve ter no máximo 500 caracteres")
    private String comment;

    public AssessmentCreateRequest(){}

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
