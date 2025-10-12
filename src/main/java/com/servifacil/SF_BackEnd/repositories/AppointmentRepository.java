package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, Integer> {

    // Buscar agendamentos por cliente
    List<AppointmentModel> findByClientId(int clientId);

    // Buscar agendamentos por serviço
    List<AppointmentModel> findByServiceId(int serviceId);

    // Verificar se já existe agendamento no mesmo horário para o mesmo serviço
    @Query("SELECT a FROM AppointmentModel a WHERE a.serviceId = :serviceId AND a.appointmentDate = :appointmentDate AND a.appointmentStatus != 'CANCELLED'")
    List<AppointmentModel> findExistingAppointments(@Param("serviceId") int serviceId, @Param("appointmentDate") LocalDateTime appointmentDate);

    // Buscar agendamentos por status
    List<AppointmentModel> findByAppointmentStatus(AppointmentModel.AppointmentStatus status);
}