package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {

    Optional<CategoryModel> findByCategory(String category);
}