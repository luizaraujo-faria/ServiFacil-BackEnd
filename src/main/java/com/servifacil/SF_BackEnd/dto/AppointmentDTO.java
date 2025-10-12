package com.servifacil.SF_BackEnd.dto;

import com.servifacil.SF_BackEnd.models.AppointmentModel;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private int appointmentId;
    private int serviceId;
    private int clientId;
    private LocalDateTime appointmentDate;
    private AppointmentModel.AppointmentStatus appointmentStatus;
    private LocalDateTime createdAt;

    // Construtores
    public AppointmentDTO() {}

    public AppointmentDTO(int serviceId, int clientId, LocalDateTime appointmentDate) {
        this.serviceId = serviceId;
        this.clientId = clientId;
        this.appointmentDate = appointmentDate;
        this.appointmentStatus = AppointmentModel.AppointmentStatus.PENDING;
    }

    // Getters e Setters
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public AppointmentModel.AppointmentStatus getAppointmentStatus() { return appointmentStatus; }
    public void setAppointmentStatus(AppointmentModel.AppointmentStatus appointmentStatus) { this.appointmentStatus = appointmentStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}