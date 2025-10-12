package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, Integer> {
}