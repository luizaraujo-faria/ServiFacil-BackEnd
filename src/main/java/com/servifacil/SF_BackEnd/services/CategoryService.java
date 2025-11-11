package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.dto.CreateCategoryDTO;
import com.servifacil.SF_BackEnd.exceptions.ApiException;
import com.servifacil.SF_BackEnd.models.CategoryModel;
import com.servifacil.SF_BackEnd.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lista Categorias
    public List<CategoryModel> listCategories(){

        List<CategoryModel> categories = categoryRepository.findAll();
        if(categories.isEmpty() || categories == null){
            throw new ApiException("Nenhuma categoria encontrada!", HttpStatus.NOT_FOUND);
        }

        return categories;
    }

    public CategoryModel createCategory(CreateCategoryDTO request){

        CategoryModel existingCategory = categoryRepository.findByCategory(request.getCategory())
                .orElseThrow(() -> new ApiException("Categoria já existe", HttpStatus.CONFLICT));

        categoryRepository.spInsertCategory(
                request.getCategory(),
                request.getDetails()
        );

        return existingCategory;
    }
}
