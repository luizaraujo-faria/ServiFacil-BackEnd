package com.servifacil.SF_BackEnd.controllers;

import com.servifacil.SF_BackEnd.models.CategoryModel;
import com.servifacil.SF_BackEnd.responses.EntityResponse;
import com.servifacil.SF_BackEnd.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<EntityResponse<?>> listCategories(){

        List<CategoryModel> categories = categoryService.listCategories();

        EntityResponse<?> getResponse = new EntityResponse<>(
                true,
                "Categorias listadas com sucesso",
                categories
        );

        return ResponseEntity.status(HttpStatus.OK).body(getResponse);
    }
}
