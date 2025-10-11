package com.servifacil.SF_BackEnd.controllers;

import com.servifacil.SF_BackEnd.dto.UserAddressDTO;
import com.servifacil.SF_BackEnd.dto.UserLoginDTO;
import com.servifacil.SF_BackEnd.dto.UserUpdateDTO;
import com.servifacil.SF_BackEnd.models.UserModel;
import com.servifacil.SF_BackEnd.responses.LoginResponse;
import com.servifacil.SF_BackEnd.security.JwtUtil;
import com.servifacil.SF_BackEnd.services.UserService;
import com.servifacil.SF_BackEnd.exceptions.ApiException;
import com.servifacil.SF_BackEnd.responses.EntityResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody UserAddressDTO request,
                                                          BindingResult bindingResult) {

        // Se houver erros de validação, lança ApiException
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ApiException(errors, HttpStatus.BAD_REQUEST);
        }

        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Usuário criado com sucesso!"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse<UserModel>> userLogin(@Valid @RequestBody UserLoginDTO request,
                                                         BindingResult bindingResult) {

        // Se houver erros de validação, lança ApiException
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ApiException(errors, HttpStatus.BAD_REQUEST);
        }

        UserModel user = userService.userLogin(request);

        String token = jwtUtil.generateToken(user.getUserId() ,user.getEmail());

        LoginResponse<UserModel> response = new LoginResponse<>(
                true,
                "Login realizado com sucesso!",
                token
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<EntityResponse<?>> userUpdate(@PathVariable int id,
                                                        @Valid @RequestBody UserAddressDTO request,
                                                        BindingResult bindingResult,
                                                        HttpServletRequest serverletReq){

        // Se houver erros de validação, lança ApiException
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ApiException(errors, HttpStatus.BAD_REQUEST);
        }

        int idFromToken = (int) serverletReq.getAttribute("userId");

        if(id != idFromToken){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        EntityResponse<?> response = new EntityResponse<>(
                true,
                "Dados atualizados com sucesso!",
                null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}