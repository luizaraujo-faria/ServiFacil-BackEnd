package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Integer> {

    @Procedure(name = "spInsertAddress")
    void spInsertAddress(
            @Param("vZip_Code") String zipCode,
            @Param("vStreet") String street,
            @Param("vHouse_Number") String houseNumber,
            @Param("vComplement") String complement,
            @Param("vNeighborhood") String neighborhood,
            @Param("vCity") String city,
            @Param("vState") String state,
            @Param("vAddress_Id") int[] addressId // out param
    );
}
