package com.servifacil.SF_BackEnd.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCategoryDTO {

    @Column(name = "Category")
    @NotBlank(message = "Categoria é obrigatória!")
    @Size(min = 2, max = 80, message = "Categoria deve conter entre 2 e 80 caractéres!")
    private String category;

    @Column(name = "Details")
    @Size(min = 2, max = 150, message = "detalhes da categoria deve conter entre 2 e 150 caractéres!")
    private String details;

    // GETTERS & SETTERS

    public String getCategory(){ return this.category; }
    public void setCategory(String category){ this.category = category; }

    public String getDetails(){ return this.details; }
    public void setDetails(String details){ this.details = details; }
}
