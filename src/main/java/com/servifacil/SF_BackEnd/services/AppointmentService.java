package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.dto.CreateAppointmentDTO;
import com.servifacil.SF_BackEnd.exceptions.ApiException;
import com.servifacil.SF_BackEnd.models.ServiceModel;
import com.servifacil.SF_BackEnd.repositories.AppointmentRepository;
import com.servifacil.SF_BackEnd.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Criar agendamento
    public int createAppointment(int clientId, int serviceId, CreateAppointmentDTO request) {

        ServiceModel existingService = serviceRepository.findById(serviceId)
                .filter(s -> "Ativo".equalsIgnoreCase(s.getServiceStatus().getDisplayName().trim()))
                .orElseThrow(() -> new ApiException("Serviço não econtrado para agendamento!", HttpStatus.NOT_FOUND));

        boolean conflictDates = appointmentRepository.existsByService_ServiceIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
          serviceId,
          request.getEndDate(),
          request.getStartDate()
        );

        if(request.getEndDate().isBefore(request.getStartDate()) || request.equals(request.getStartDate())){
            throw new ApiException("Data de término deve ser maior que a de início!", HttpStatus.BAD_REQUEST);
        }

        if(conflictDates){
            throw new ApiException("Horário indisponível para este serviço!", HttpStatus.CONFLICT);
        }

        int newAppointment = appointmentRepository.spInsertAppointment(
                clientId,
                serviceId,
                request.getStartDate(),
                request.getEndDate()
        );

        return newAppointment;
    }

//    // Buscar todos os agendamentos
//    public List<AppointmentModel> getAllAppointments() {
//        return appointmentRepository.findAll();
//    }
//
//    // Buscar agendamentos por cliente
//    public List<AppointmentModel> getAppointmentsByClient(int clientId) {
//        return appointmentRepository.findByClientId(clientId);
//    }
//
//    // Buscar agendamentos por serviço
//    public List<AppointmentModel> getAppointmentsByService(int serviceId) {
//        return appointmentRepository.findByServiceId(serviceId);
//    }
//
//    // Buscar agendamento por ID
//    public Optional<AppointmentModel> getAppointmentById(int appointmentId) {
//        return appointmentRepository.findById(appointmentId);
//    }
//
//    // Atualizar agendamento
//    public AppointmentModel updateAppointment(int appointmentId, AppointmentDTO appointmentDTO) {
//        Optional<AppointmentModel> appointmentOpt = appointmentRepository.findById(appointmentId);
//        if (appointmentOpt.isEmpty()) {
//            throw new RuntimeException("Agendamento não encontrado!");
//        }
//
//        AppointmentModel appointment = appointmentOpt.get();
//
//        // Validar se pode editar (apenas agendamentos pendentes)
//        if (appointment.getAppointmentStatus() != AppointmentModel.AppointmentStatus.PENDING) {
//            throw new RuntimeException("Somente agendamentos pendentes podem ser editados!");
//        }
//
//        // Validar nova data se for diferente
//        if (!appointment.getAppointmentDate().equals(appointmentDTO.getAppointmentDate())) {
//            // Validar disponibilidade do novo horário
//            List<AppointmentModel> existingAppointments = appointmentRepository.findExistingAppointments(
//                    appointmentDTO.getServiceId(),
//                    appointmentDTO.getAppointmentDate()
//            );
//
//            if (!existingAppointments.isEmpty()) {
//                throw new RuntimeException("Já existe um agendamento para este horário!");
//            }
//
//            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
//        }
//
//        appointment.setServiceId(appointmentDTO.getServiceId());
//
//        return appointmentRepository.save(appointment);
//    }
//
//    // Cancelar agendamento
//    public AppointmentModel cancelAppointment(int appointmentId) {
//        Optional<AppointmentModel> appointmentOpt = appointmentRepository.findById(appointmentId);
//        if (appointmentOpt.isEmpty()) {
//            throw new RuntimeException("Agendamento não encontrado!");
//        }
//
//        AppointmentModel appointment = appointmentOpt.get();
//
//        // Validar se pode cancelar (apenas agendamentos pendentes)
//        if (appointment.getAppointmentStatus() != AppointmentModel.AppointmentStatus.PENDING) {
//            throw new RuntimeException("Somente agendamentos pendentes podem ser cancelados!");
//        }
//
//        appointment.setAppointmentStatus(AppointmentModel.AppointmentStatus.CANCELLED);
//        return appointmentRepository.save(appointment);
//    }
//
//    // Completar agendamento
//    public AppointmentModel completeAppointment(int appointmentId) {
//        Optional<AppointmentModel> appointmentOpt = appointmentRepository.findById(appointmentId);
//        if (appointmentOpt.isEmpty()) {
//            throw new RuntimeException("Agendamento não encontrado!");
//        }
//
//        AppointmentModel appointment = appointmentOpt.get();
//        appointment.setAppointmentStatus(AppointmentModel.AppointmentStatus.COMPLETED);
//        return appointmentRepository.save(appointment);
//    }
}