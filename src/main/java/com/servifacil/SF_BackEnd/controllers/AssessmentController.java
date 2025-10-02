package com.servifacil.SF_BackEnd.controllers;

import com.servifacil.SF_BackEnd.dto.AssessmentCreateRequest;
import com.servifacil.SF_BackEnd.dto.AssessmentResponse;
import com.servifacil.SF_BackEnd.dto.AssessmentUpdateRequest;
import com.servifacil.SF_BackEnd.services.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody AssessmentCreateRequest request) {
        try {
            AssessmentResponse resp = assessmentService.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Dados inválidos: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno no servidor"));
        }
    }

    @GetMapping("/servico/{serviceId}")
    public ResponseEntity<?> listarPorServico(@PathVariable int serviceId) {
        try {
            List<AssessmentResponse> list = assessmentService.listarPorServico(serviceId);
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Dados inválidos: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno no servidor"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") int id,
                                    @Valid @RequestBody AssessmentUpdateRequest request) {
        try {
            AssessmentResponse resp = assessmentService.editar(id, request);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Dados inválidos: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno no servidor"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") int id) {
        try {
            assessmentService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Dados inválidos: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno no servidor"));
        }
    }

    public static class ErrorResponse {
        private String message;
        private long timestamp;

        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }

        public String getMessage() { return message; }
        public long getTimestamp() { return timestamp; }
    }
}
