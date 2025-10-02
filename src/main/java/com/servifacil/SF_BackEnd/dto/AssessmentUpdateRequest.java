package com.servifacil.SF_BackEnd.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class AssessmentUpdateRequest {
    @Min(value = 1, message = "score deve ser entre 1 e 5")
    @Max(value = 5, message = "score deve ser entre 1 e 5")
    private Integer score;

    @Size(max = 500, message = "Comentário deve ter no máximo 500 caracteres")
    private String comment;

    public AssessmentUpdateRequest(){}

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
