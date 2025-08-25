package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.dto.AddressDTO;
import com.servifacil.SF_BackEnd.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    public final AddressRepository addressRepository;
    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public int createAddress(AddressDTO dto){

        int[] newAddressId = new int[1]; // captura o OUT
        addressRepository.spInsertAddress(
                dto.getZipCode(),
                dto.getStreet(),
                dto.getHouseNumber(),
                dto.getComplement(),
                dto.getNeighborhood(),
                dto.getCity(),
                dto.getState(),
                newAddressId
        );
        System.out.println("DADOS CHEGANDO:  " + dto.getZipCode());
        return newAddressId[0];
    }
}
