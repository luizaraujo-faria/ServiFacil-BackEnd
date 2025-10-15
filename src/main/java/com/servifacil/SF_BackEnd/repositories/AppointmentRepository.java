package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, Integer> {

    @Override
    Optional<AppointmentModel> findById(Integer integer);

    boolean existsByService_ServiceIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            int serviceId,
            LocalDateTime endDate,
            LocalDateTime startDate
    );

    @Procedure(procedureName = "spInsertAppointment")
    int spInsertAppointment(
            @Param("vClient_ID") int clientId,
            @Param("vService_ID") int serviceId,
            @Param("vStart_Date") LocalDateTime startDate,
            @Param("vEnd_Date") LocalDateTime endDate
            );
}
