package com.servifacil.SF_BackEnd.repositories;

import com.servifacil.SF_BackEnd.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {

    Optional<CategoryModel> findByCategory(String category);

    @Procedure(name = "spInsertCategory")
    public void spInsertCategory(
            @Param("vCategory") String category,
            @Param("vDetails") String details
    );
}