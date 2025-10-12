package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.dto.ServiceDTO;
import com.servifacil.SF_BackEnd.repositories.ServiceRepository;
import com.servifacil.SF_BackEnd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    public int createService(int userId, ServiceDTO request) {

        int newService = serviceRepository.spInsertService(
                userId,
                request.getTitle(),
                request.getPrice(),
                request.getDetails(),
                request.getServiceStatus(),
                request.getCategory()
        );

        return newService;
    }

//    public List<ServiceModel> listServices() {
//
//    }
//
//    public List<ServiceModel> getServiceByName(String name) {
//
//    }
//
//    public List<ServiceModel> getServicerByCategory(int categoryId) {
//
//    }
//
//    public List<ServiceModel> getServiceByService(int profissionalId) {
//
//    }
//
//    public ServiceModel updateservice(int id, ServiceModel novoServico) {
//
//    }
//
//    public void excluirServico(int id) {
//
//    }
}