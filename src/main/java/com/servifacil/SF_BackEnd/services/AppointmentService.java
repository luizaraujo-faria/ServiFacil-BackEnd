package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.dto.CreateAppointmentDTO;
import com.servifacil.SF_BackEnd.exceptions.ApiException;
import com.servifacil.SF_BackEnd.models.AppointmentModel;
import com.servifacil.SF_BackEnd.models.ServiceModel;
import com.servifacil.SF_BackEnd.models.UserModel;
import com.servifacil.SF_BackEnd.repositories.AppointmentRepository;
import com.servifacil.SF_BackEnd.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
                request.getEndDate(),
                AppointmentModel.AppointmentStatus.Pendente.getDisplayName()
        );

        return newAppointment;
    }

    // Buscar todos os agendamentos
    @Transactional
    public List<AppointmentModel> getAppointmentsByUser(int clientId, String apStatus) {

        List<AppointmentModel> userAppointments = appointmentRepository.findAllAppointmentByUserByStatus(clientId, apStatus);
        if(userAppointments == null || userAppointments.isEmpty()){
            throw new ApiException("Nenhum agendamento encontrado!", HttpStatus.NOT_FOUND);
        }

        return userAppointments;
    }

    // Buscar agendamentos por serviço
    public List<AppointmentModel> getAppointmentsByService(int professionalId, int serviceId, String apStatus) {

        ServiceModel existingService = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ApiException("Serviço não encontrado!", HttpStatus.NOT_FOUND));

        if (existingService.getProfessional().getUserId() != professionalId) {
            throw new ApiException("Este serviço pertence a outro profissional!", HttpStatus.CONFLICT);
        }

        List<AppointmentModel> serviceAppointments = appointmentRepository.findAllAppointmentByServiceByStatus(serviceId, apStatus);
        if (serviceAppointments == null || serviceAppointments.isEmpty()) {
            throw new ApiException("Nenhum agendamento encontrado para este serviço!", HttpStatus.NOT_FOUND);
        }

        return serviceAppointments;
    }

    // Cancelar agendamento
    public int cancelAppointment(int clientId, int appointmentId) {

        AppointmentModel existingAppointment = appointmentRepository.findById(appointmentId)
                .filter(a -> "Pendente".equalsIgnoreCase(a.getAppointmentStatus().getDisplayName()))
                .orElseThrow(() -> new ApiException("Agendamento não encontrado!", HttpStatus.NOT_FOUND));

        if(existingAppointment.getClient().getUserId() != clientId){
            throw new ApiException("Este agendamento não pertence a você!", HttpStatus.CONFLICT);
        }

        return appointmentRepository.updateAppointmentStatus(appointmentId, AppointmentModel.AppointmentStatus.Cancelado.getDisplayName());
    }

    // Completar agendamento
    public int concludeAppointment(int clientId, int appointmentId) {

        AppointmentModel existingAppointment = appointmentRepository.findById(appointmentId)
                .filter(a -> "Pendente".equalsIgnoreCase(a.getAppointmentStatus().getDisplayName()))
                .orElseThrow(() -> new ApiException("Agendamento não encontrado!", HttpStatus.NOT_FOUND));

        if(existingAppointment.getClient().getUserId() != clientId){
            throw new ApiException("Este agendamento não pertence a você!", HttpStatus.CONFLICT);
        }

        return appointmentRepository.updateAppointmentStatus(appointmentId, AppointmentModel.AppointmentStatus.Concluido.getDisplayName());
    }


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
}