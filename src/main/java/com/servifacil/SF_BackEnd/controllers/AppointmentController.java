package com.servifacil.SF_BackEnd.controllers;

import com.servifacil.SF_BackEnd.dto.CreateAppointmentDTO;
import com.servifacil.SF_BackEnd.exceptions.ApiException;
import com.servifacil.SF_BackEnd.models.AppointmentModel;
import com.servifacil.SF_BackEnd.responses.EntityResponse;
import com.servifacil.SF_BackEnd.services.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Criar agendamento
    @PostMapping("/{id}/{serviceId}")
    public ResponseEntity<EntityResponse<?>> createAppointment(@PathVariable int id,
                                                            @PathVariable int serviceId,
                                                            @Valid @RequestBody CreateAppointmentDTO request,
                                                            BindingResult bindingResult,
                                                            HttpServletRequest servletReq) {

        // Se houver erros de validação, lança ApiException
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ApiException(errors, HttpStatus.BAD_REQUEST);
        }

        Integer idFromToken = (Integer) servletReq.getAttribute("userId");

        if(idFromToken == null){
            throw new ApiException("Usuário não autenticado ou token inválido!", HttpStatus.UNAUTHORIZED);
        }

        if(id != idFromToken){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new EntityResponse<>(false, "Você não tem permissão para agendar serviços por outro usuário!", null));
        }

        int newAppointment = appointmentService.createAppointment(id, serviceId, request);

        EntityResponse<Integer> createResponse = new EntityResponse<>(
                true,
                "Agendamento criado com sucesso!",
                newAppointment
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }

//    // Buscar todos os agendamentos
//    @GetMapping
//    public ResponseEntity<List<AppointmentModel>> getAllAppointments() {
//        List<AppointmentModel> appointments = appointmentService.getAllAppointments();
//        return ResponseEntity.ok(appointments);
//    }
//
//    // Buscar agendamento por ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getAppointmentById(@PathVariable int id) {
//        Optional<AppointmentModel> appointment = appointmentService.getAppointmentById(id);
//        if (appointment.isPresent()) {
//            return ResponseEntity.ok(appointment.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Buscar agendamentos por cliente
//    @GetMapping("/client/{clientId}")
//    public ResponseEntity<List<AppointmentModel>> getAppointmentsByClient(@PathVariable int clientId) {
//        List<AppointmentModel> appointments = appointmentService.getAppointmentsByClient(clientId);
//        return ResponseEntity.ok(appointments);
//    }
//
//    // Buscar agendamentos por serviço
//    @GetMapping("/service/{serviceId}")
//    public ResponseEntity<List<AppointmentModel>> getAppointmentsByService(@PathVariable int serviceId) {
//        List<AppointmentModel> appointments = appointmentService.getAppointmentsByService(serviceId);
//        return ResponseEntity.ok(appointments);
//    }
//
//    // Atualizar agendamento
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateAppointment(@PathVariable int id, @RequestBody AppointmentDTO appointmentDTO) {
//        try {
//            AppointmentModel appointment = appointmentService.updateAppointment(id, appointmentDTO);
//            return ResponseEntity.ok(appointment);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // Cancelar agendamento
//    @PutMapping("/{id}/cancel")
//    public ResponseEntity<?> cancelAppointment(@PathVariable int id) {
//        try {
//            AppointmentModel appointment = appointmentService.cancelAppointment(id);
//            return ResponseEntity.ok(appointment);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // Completar agendamento
//    @PutMapping("/{id}/complete")
//    public ResponseEntity<?> completeAppointment(@PathVariable int id) {
//        try {
//            AppointmentModel appointment = appointmentService.completeAppointment(id);
//            return ResponseEntity.ok(appointment);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}