package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceModel, Integer> {
    List<ServiceModel> findByTitleContainingIgnoreCase(String title);
    List<ServiceModel> findByCategoryId(int categoryId);
    List<ServiceModel> findByProfessionalId(int professionalId);
}
