package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.dto.AssessmentCreateRequest;
import com.servifacil.SF_BackEnd.dto.AssessmentResponse;
import com.servifacil.SF_BackEnd.dto.AssessmentUpdateRequest;
import com.servifacil.SF_BackEnd.models.AssessmentModel;
import com.servifacil.SF_BackEnd.models.ServiceModel;
import com.servifacil.SF_BackEnd.repositories.AssessmentRepository;
import com.servifacil.SF_BackEnd.repositories.ServiceRepository;
import com.servifacil.SF_BackEnd.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final ServiceRepository serviceRepository; // vem da PR
    private final UserRepository userRepository;

    public AssessmentService(AssessmentRepository assessmentRepository,
                             ServiceRepository serviceRepository,
                             UserRepository userRepository) {
        this.assessmentRepository = assessmentRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AssessmentResponse criar(AssessmentCreateRequest req) {
        // 1) valida usuário (cliente) existe
        if (!userRepository.existsById(req.getClientId())) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        // 2) valida serviço existe e está ATIVO
        ServiceModel service = serviceRepository.findById(req.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        boolean ativo = "ACTIVE".equalsIgnoreCase(String.valueOf(service.getServiceStatus()));
        if (!ativo) {
            throw new IllegalArgumentException("Serviço indisponível para avaliação");
        }

        AssessmentModel entity = new AssessmentModel();
        entity.setServiceId(req.getServiceId());
        entity.setClientId(req.getClientId());
        entity.setScore(req.getScore());
        entity.setComment(req.getComment());

        AssessmentModel saved = assessmentRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AssessmentResponse> listarPorServico(int serviceId) {
        // garante que o serviço existe (mesmo se INATIVO, você ainda pode querer listar avaliações)
        serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        return assessmentRepository.findByServiceId(serviceId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public AssessmentResponse editar(int assessmentId, AssessmentUpdateRequest req) {
        AssessmentModel entity = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Avaliação não encontrada"));

        if (req.getScore() != null) entity.setScore(req.getScore());
        if (req.getComment() != null) entity.setComment(req.getComment());

        AssessmentModel saved = assessmentRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional
    public void excluir(int assessmentId) {
        if (!assessmentRepository.existsById(assessmentId)) {
            throw new IllegalArgumentException("Avaliação não encontrada");
        }
        assessmentRepository.deleteById(assessmentId);
    }

    private AssessmentResponse toResponse(AssessmentModel a) {
        return new AssessmentResponse(
                a.getAssessmentId(),
                a.getServiceId(),
                a.getClientId(),
                a.getScore(),
                a.getComment()
        );
    }
}
