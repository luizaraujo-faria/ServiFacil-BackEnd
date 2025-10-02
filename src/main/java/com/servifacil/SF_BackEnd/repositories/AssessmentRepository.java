package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.AssessmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<AssessmentModel, Integer> {
    List<AssessmentModel> findByServiceId(int serviceId);
    List<AssessmentModel> findByClientId(int clientId);
    boolean existsByServiceIdAndClientId(int serviceId, int clientId); //evita duplicidade
}
