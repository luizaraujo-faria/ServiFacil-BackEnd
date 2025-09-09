package com.servifacil.SF_BackEnd.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "tbAppointments")
public class AppointmentModel {

    @Id
    @Column(name = "Appointment_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;

    @Column(name = "Service_ID")
    private int serviceId;

    @Column(name = "Client_ID")
    private int clientId;

    @Column(name = "A_Date")
    @NotBlank
    private Date appointmentDate = new Date();

    @Column(name = "A_Status")
    private AppointmentStatus appointmentStatus;

    enum AppointmentStatus {
        PENDING, COMPLETED;
    }

    // GETTERS & SETTERS

    public int getAppointmentId(){ return this.appointmentId; }

    public int getServiceId(){ return this.serviceId; }
    public void setServiceId(int serviceId){ this.serviceId = serviceId; }

    public int getClientId(){ return this.clientId; }
    public void setClientId(int clientId){ this.clientId = clientId; }

    public Date getAppointmentDate(){ return this.appointmentDate; }
    public void setAppointmentDate(Date appointmentDate){ this.appointmentDate = appointmentDate; }

    public AppointmentStatus getAppointmentStatus(){ return this.appointmentStatus; }
    public void setAppointmentStatus(AppointmentStatus appointmentStatus){ this.appointmentStatus = appointmentStatus; }
}
