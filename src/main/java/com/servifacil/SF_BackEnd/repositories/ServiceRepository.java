package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ServiceRepository extends JpaRepository<ServiceModel, Integer> {

    @Procedure(procedureName = "spInsertService")
    Integer spInsertService(
            @Param("vProfessional_ID") int professionalId,
            @Param("vTitle") String title,
            @Param("vPrice") BigDecimal price,
            @Param("vDetails") String details,
            @Param("vService_Status") String serviceStatus,
            @Param("vCategory") String category
    );
}
