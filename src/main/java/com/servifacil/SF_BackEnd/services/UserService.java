package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.dto.AddressDTO;
import com.servifacil.SF_BackEnd.dto.UserCreateRequest;
import com.servifacil.SF_BackEnd.models.UserModel;
import com.servifacil.SF_BackEnd.repositories.UserRepository;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserModel createUser(@NonNull UserCreateRequest request) {
        // Criar endereço usando a procedure
        // PRIMEIRO crie um objeto AddressDTO com os dados da request
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setZipCode(request.getZipCode());
        addressDTO.setStreet(request.getStreet());
        addressDTO.setHouseNumber(request.getHouseNumber());
        addressDTO.setComplement(request.getComplement());
        addressDTO.setNeighborhood(request.getNeighborhood());
        addressDTO.setCity(request.getCity());
        addressDTO.setState(request.getState());

        // AGORA passe apenas o DTO para o service
        int addressId = addressService.createAddress(addressDTO);

        // Criar usuário
        UserModel user = new UserModel();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());

        // CRIPTOGRAFIA DA SENHA
        user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));

        user.setCpf(request.getCpf());
        user.setRg(request.getRg());
        user.setTelephone(request.getTelephone());
        user.setCnpj(request.getCnpj());
        user.setBirthDate(request.getBirthDate());
        user.setUserType(UserModel.UserType.valueOf(request.getUserType()));
        user.setProfession(request.getProfession());
        user.setAddressId(addressId);

        return userRepository.save(user);
    }

    // Método para verificar senha no login
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}