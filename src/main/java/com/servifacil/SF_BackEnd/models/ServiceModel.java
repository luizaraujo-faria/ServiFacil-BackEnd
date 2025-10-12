package com.servifacil.SF_BackEnd.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tbServices")
public class ServiceModel {

    @Id
    @Column(name = "Service_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;

    @Column(name = "Title")
    @NotBlank
    private String title;

    @Column(name = "Price")
    @NotBlank
    private double price;

    @Column(name = "Details")
    @NotBlank
    private String details;

    @Column(name = "Professional_ID")
    private int professionalId;

    @Column(name = "Category_ID")
    private int categoryId;

    @Column(name = "Service_Status")
    private ServiceStatus serviceStatus;

    public enum ServiceStatus {
        ACTIVE, INACTIVE;
    }

    // GETTERS & SETTERS

    public int getServiceId(){ return this.serviceId; }

    public String getTitle(){ return this.title; }
    public void setTitle(String title){ this.title = title; }

    public double getPrice(){ return this.price; }
    public void setPrice(double price){ this.price = price; }

    public String getDetails(){ return this.details; }
    public void setDetails(String details){ this.details = details; }

    public int getProfessionalId(){ return this.professionalId; }
    public void setProfessionalId(int professionalId){ this.professionalId = professionalId; }

    public int getCategoryId(){ return this.categoryId; }
    public void setCategoryId(int categoryId){ this.categoryId = categoryId; }

    public ServiceStatus getServiceStatus(){ return this.serviceStatus; }
    public void setServiceStatus(ServiceStatus serviceStatus){ this.serviceStatus = serviceStatus; }
}
