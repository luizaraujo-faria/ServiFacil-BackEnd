package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.dto.UserAddressDTO;
import com.servifacil.SF_BackEnd.dto.UserLoginDTO;
import com.servifacil.SF_BackEnd.dto.UserUpdateDTO;
import com.servifacil.SF_BackEnd.models.UserModel;
import com.servifacil.SF_BackEnd.repositories.UserRepository;
import com.servifacil.SF_BackEnd.exceptions.ApiException;
import com.servifacil.SF_BackEnd.utils.MergeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void createUser(UserAddressDTO request) {

        String email = request.getEmail().trim().toLowerCase();
        System.out.println("Email recebido: [" + email + "]");

        if(userRepository.existsByEmail(email)){
            System.out.println("Email já cadastrado!");
            throw new ApiException("Email já cadastrado!", HttpStatus.CONFLICT);
        }

        // CONVERTER LocalDate para java.sql.Date
        java.sql.Date birthDate = java.sql.Date.valueOf(request.getBirthDate());
        // ✅ CONVERSÃO USANDO O ENUM DA USERMODEL
//        UserModel.UserType userType = UserModel.UserType.fromString(request.getUserType());
//        byte userTypeByte = userType.getCode();
        System.out.println("UserType convertido: " + request.getUserType());

        // CRIPTOGRAFIA DA SENHA
        request.setUserPassword(passwordEncoder.encode(request.getUserPassword()));

        userRepository.spInsertUser(
                request.getUserName(),
                request.getEmail(),
                request.getUserPassword(),
                request.getCpf(),
                request.getRg(),
                request.getTelephone(),
                request.getCnpj(),
                birthDate,
                request.getUserType(),
                request.getProfession(),
                request.getZipCode(),
                request.getStreet(),
                request.getHouseNumber(),
                request.getComplement(),
                request.getNeighborhood(),
                request.getCity(),
                request.getState()
        );
    }

    @Transactional
    public UserModel userLogin(UserLoginDTO request){

        String normalizedEmail = request.getEmail().trim().toLowerCase();
        System.out.println("Email recebido: " + normalizedEmail);

        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException("Usuário não encontrado!", HttpStatus.NOT_FOUND));
        System.out.println("Usuário encontrado no banco: " + user.getUserId() + " - " + user.getEmail());

        if(!passwordEncoder.matches(request.getUserPassword(), user.getUserPassword())){
            throw new ApiException("Senha incorreta!", HttpStatus.BAD_REQUEST);
        }

        return user;
    }

    @Transactional
    public UserModel getUser(int userId){

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("Usuário não encontrado!", HttpStatus.NOT_FOUND));

        return user;
    }

    @Transactional
    public UserModel updateUser(int paramId, UserUpdateDTO request){

        System.out.println("ID RECEBIDO: " + paramId);

        UserModel existingUser = userRepository.findById(paramId)
                .orElseThrow(() -> new ApiException("Usuário não encontrado!", HttpStatus.NOT_FOUND));

        if (request == null){
            throw new ApiException("Ao menos um campo deve ser preenchido!", HttpStatus.BAD_REQUEST);
        }

        request.setUserPassword(passwordEncoder.encode(request.getUserPassword()));
        java.sql.Date birthDate = java.sql.Date.valueOf(request.getBirthDate());

//        MergeUtils.mergeNonNullFields(request, existingUser);

        userRepository.spUpdateUser(
                paramId,
                request.getUserName(),
                request.getEmail(),
                request.getUserPassword(),
                request.getCpf(),
                request.getRg(),
                request.getTelephone(),
                request.getCnpj(),
                birthDate,
                request.getUserType(),
                request.getProfession(),
                request.getZipCode(),
                request.getStreet(),
                request.getHouseNumber(),
                request.getComplement(),
                request.getNeighborhood(),
                request.getCity(),
                request.getState()
        );

        System.out.println("USUARIO ATUALIZADO: " + existingUser);

        return existingUser;
    }
}