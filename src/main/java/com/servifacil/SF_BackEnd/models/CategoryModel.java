package com.servifacil.SF_BackEnd.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tbCategories")
public class CategoryModel {

    @Id
    @Column(name = "Category_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column(name = "Category")
    @NotBlank
    private String category;

    @Column(name = "Details")
    private String details;

    // GETTERS & SETTERS

    public int getCategoryId(){ return this.categoryId; }

    public String getCategory(){ return this.category; }
    public void setCategory(String category){ this.category = category; }

    public String getDetails(){ return this.details; }
    public void setDetails(String details){ this.details = details; }
}
