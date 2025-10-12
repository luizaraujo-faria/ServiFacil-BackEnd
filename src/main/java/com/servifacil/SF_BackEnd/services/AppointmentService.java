package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.dto.AppointmentDTO;
import com.servifacil.SF_BackEnd.models.AppointmentModel;
import com.servifacil.SF_BackEnd.models.ServiceModel;
import com.servifacil.SF_BackEnd.repositories.AppointmentRepository;
import com.servifacil.SF_BackEnd.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Criar agendamento
    public AppointmentModel createAppointment(AppointmentDTO appointmentDTO) {
        // Validar se o serviço existe e está ativo
        Optional<ServiceModel> serviceOpt = serviceRepository.findById(appointmentDTO.getServiceId());
        if (serviceOpt.isEmpty()) {
            throw new RuntimeException("Serviço não encontrado!");
        }

        ServiceModel service = serviceOpt.get();
        if (service.getServiceStatus() != ServiceModel.ServiceStatus.ACTIVE) {
            throw new RuntimeException("Serviço não está disponível!");
        }

        // Validar se a data é futura
        if (appointmentDTO.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("A data do agendamento deve ser futura!");
        }

        // Validar disponibilidade do horário
        List<AppointmentModel> existingAppointments = appointmentRepository.findExistingAppointments(
                appointmentDTO.getServiceId(),
                appointmentDTO.getAppointmentDate()
        );

        if (!existingAppointments.isEmpty()) {
            throw new RuntimeException("Já existe um agendamento para este horário!");
        }

        // Criar o agendamento
        AppointmentModel appointment = new AppointmentModel();
        appointment.setServiceId(appointmentDTO.getServiceId());
        appointment.setClientId(appointmentDTO.getClientId());
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setAppointmentStatus(AppointmentModel.AppointmentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }

    // Buscar todos os agendamentos
    public List<AppointmentModel> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Buscar agendamentos por cliente
    public List<AppointmentModel> getAppointmentsByClient(int clientId) {
        return appointmentRepository.findByClientId(clientId);
    }

    // Buscar agendamentos por serviço
    public List<AppointmentModel> getAppointmentsByService(int serviceId) {
        return appointmentRepository.findByServiceId(serviceId);
    }

    // Buscar agendamento por ID
    public Optional<AppointmentModel> getAppointmentById(int appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    // Atualizar agendamento
    public AppointmentModel updateAppointment(int appointmentId, AppointmentDTO appointmentDTO) {
        Optional<AppointmentModel> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            throw new RuntimeException("Agendamento não encontrado!");
        }

        AppointmentModel appointment = appointmentOpt.get();

        // Validar se pode editar (apenas agendamentos pendentes)
        if (appointment.getAppointmentStatus() != AppointmentModel.AppointmentStatus.PENDING) {
            throw new RuntimeException("Somente agendamentos pendentes podem ser editados!");
        }

        // Validar nova data se for diferente
        if (!appointment.getAppointmentDate().equals(appointmentDTO.getAppointmentDate())) {
            // Validar disponibilidade do novo horário
            List<AppointmentModel> existingAppointments = appointmentRepository.findExistingAppointments(
                    appointmentDTO.getServiceId(),
                    appointmentDTO.getAppointmentDate()
            );

            if (!existingAppointments.isEmpty()) {
                throw new RuntimeException("Já existe um agendamento para este horário!");
            }

            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        }

        appointment.setServiceId(appointmentDTO.getServiceId());

        return appointmentRepository.save(appointment);
    }

    // Cancelar agendamento
    public AppointmentModel cancelAppointment(int appointmentId) {
        Optional<AppointmentModel> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            throw new RuntimeException("Agendamento não encontrado!");
        }

        AppointmentModel appointment = appointmentOpt.get();

        // Validar se pode cancelar (apenas agendamentos pendentes)
        if (appointment.getAppointmentStatus() != AppointmentModel.AppointmentStatus.PENDING) {
            throw new RuntimeException("Somente agendamentos pendentes podem ser cancelados!");
        }

        appointment.setAppointmentStatus(AppointmentModel.AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }

    // Completar agendamento
    public AppointmentModel completeAppointment(int appointmentId) {
        Optional<AppointmentModel> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            throw new RuntimeException("Agendamento não encontrado!");
        }

        AppointmentModel appointment = appointmentOpt.get();
        appointment.setAppointmentStatus(AppointmentModel.AppointmentStatus.COMPLETED);
        return appointmentRepository.save(appointment);
    }
}