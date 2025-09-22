package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
