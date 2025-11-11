package com.servifacil.SF_BackEnd.controllers;

import com.servifacil.SF_BackEnd.dto.CreateCategoryDTO;
import com.servifacil.SF_BackEnd.exceptions.ApiException;
import com.servifacil.SF_BackEnd.models.CategoryModel;
import com.servifacil.SF_BackEnd.responses.EntityResponse;
import com.servifacil.SF_BackEnd.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getall")
    public ResponseEntity<EntityResponse<?>> listCategories(){

        List<CategoryModel> categories = categoryService.listCategories();

        EntityResponse<?> getResponse = new EntityResponse<>(
                true,
                "Categorias listadas com sucesso",
                categories
        );

        return ResponseEntity.status(HttpStatus.OK).body(getResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityResponse<?>> createCategory(
                                            @Valid @RequestBody CreateCategoryDTO request,
                                            BindingResult bindingResult){

        // Se houver erros de validação, lança ApiException
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ApiException(errors, HttpStatus.BAD_REQUEST);
        }

        CategoryModel newCategory = categoryService.createCategory(request);

        EntityResponse<CategoryModel> createResponse = new EntityResponse<>(
                true,
                "Categoria criada com sucesso!",
                newCategory
        );

        return ResponseEntity.status(HttpStatus.OK).body(createResponse);
    }
}
