package com.servifacil.SF_BackEnd.controllers;

import com.servifacil.SF_BackEnd.dto.ServiceDTO;
import com.servifacil.SF_BackEnd.exceptions.ApiException;
import com.servifacil.SF_BackEnd.models.ServiceModel;
import com.servifacil.SF_BackEnd.responses.EntityResponse;
import com.servifacil.SF_BackEnd.services.ServiceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PreAuthorize("hasRole('Profissional')")
    @PostMapping("/{id}")
    public ResponseEntity<EntityResponse<?>> createService(@PathVariable int id,
                                      @Valid @RequestBody ServiceDTO request,
                                      BindingResult bindingResult,
                                      HttpServletRequest servletReq) {

        // Se houver erros de validação, lança ApiException
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ApiException(errors, HttpStatus.BAD_REQUEST);
        }

        Integer idFromToken = (Integer) servletReq.getAttribute("userId");

        if (idFromToken == null) {
            throw new ApiException("Usuário não autenticado ou token inválido!", HttpStatus.UNAUTHORIZED);
        }

        if(id != idFromToken){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new EntityResponse<>(false, "Você não tem permissão para criar serviços em outro usuário!", null));
        }

        int newServiceId = serviceService.createService(id, request);

        EntityResponse<?> createResponse = new EntityResponse<>(
                true,
                "Serviço criado com sucesso!",
                newServiceId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }

//    @GetMapping("/listar")
//    public List<ServiceModel> listar() {
//        return service.listarServicos();
//    }
//
//    @GetMapping("/buscar/nome/{nome}")
//    public List<ServiceModel> buscarPorNome(@PathVariable String name) {
//        return service.buscarPorNome(name);
//    }
//
//    @GetMapping("/buscar/categoria/{id}")
//    public List<ServiceModel> buscarPorCategoria(@PathVariable int id) {
//        return service.buscarPorCategoria(id);
//    }
//
//    @PutMapping("/editar/{id}")
//    public ServiceModel editar(@PathVariable int id, @RequestBody ServiceModel service) {
//        return service.editarServico(id, service);
//    }
//
//    @DeleteMapping("/excluir/{id}")
//    public void excluir(@PathVariable int id) {
//        service.excluirServico(id);
//    }
}