package com.servifacil.SF_BackEnd.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbAppointments")
public class AppointmentModel {

    @Id
    @Column(name = "Appointment_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;

    @Column(name = "Service_ID")
    @NotNull(message = "Serviço é obrigatório!")
    private int serviceId;

    @Column(name = "Client_ID")
    @NotNull(message = "Cliente é obrigatório!")
    private int clientId;

    @Column(name = "A_Date")
    @NotNull(message = "Data de agendamento é obrigatória!")
    private LocalDateTime appointmentDate;                                          // permite agendar para data futura

    @Enumerated(EnumType.STRING)                                                    // Salva como texto no banco
    @Column(name = "A_Status")
    private AppointmentStatus appointmentStatus = AppointmentStatus.PENDING;

    @CreationTimestamp
    @Column(name = "Created_At")                                                    // Incluído no modelo
    private LocalDateTime createdAt;

    public enum AppointmentStatus {
        PENDING, COMPLETED, CANCELLED;                                              // Incluído o cancelamento
    }

    // GETTERS & SETTERS

    public int getAppointmentId(){ return this.appointmentId; }

    public int getServiceId(){ return this.serviceId; }
    public void setServiceId(int serviceId){ this.serviceId = serviceId; }

    public int getClientId(){ return this.clientId; }
    public void setClientId(int clientId){ this.clientId = clientId; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public AppointmentStatus getAppointmentStatus() { return appointmentStatus; }
    public void setAppointmentStatus(AppointmentStatus appointmentStatus) { this.appointmentStatus = appointmentStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
