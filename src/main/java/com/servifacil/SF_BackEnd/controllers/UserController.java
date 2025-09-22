package com.servifacil.SF_BackEnd.controllers;

import com.servifacil.SF_BackEnd.dto.UserCreateRequest;
import com.servifacil.SF_BackEnd.models.UserModel;
import com.servifacil.SF_BackEnd.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest request) {
        try {
            UserModel createdUser = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("Dados inválidos: " + e.getMessage())
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage())
            );
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse("Erro interno no servidor")
            );
        }
    }

    // Classe interna para padronizar respostas de erro

    public static class ErrorResponse {
        private String message;
        private long timestamp;

        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }

        // Getters
        public String getMessage() { return message; }
        public long getTimestamp() { return timestamp; }
    }
}
