package com.servifacil.SF_BackEnd.controllers;

import com.servifacil.SF_BackEnd.models.ServiceModel;
import com.servifacil.SF_BackEnd.services.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServiceController {

    private final ServiceService service;

    public ServiceController(ServiceService service) {
        this.service = service;
    }

    @PostMapping("/criar")
    public ResponseEntity<ServiceModel> criar(@RequestBody ServiceModel payload) {
        ServiceModel criado = this.service.criarServico(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/listar")
    public List<ServiceModel> listar() {
        return this.service.listarServicos();
    }

    @GetMapping("/buscar/nome/{nome}")
    public List<ServiceModel> buscarPorNome(@PathVariable("nome") String nome) {
        return this.service.buscarPorNome(nome);
    }

    @GetMapping("/buscar/categoria/{id}")
    public List<ServiceModel> buscarPorCategoria(@PathVariable("id") int id) {
        return this.service.buscarPorCategoria(id);
    }

    @PutMapping("/editar/{id}")
    public ServiceModel editar(@PathVariable("id") int id, @RequestBody ServiceModel payload) {
        return this.service.editarServico(id, payload);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable("id") int id) {
        this.service.excluirServico(id);
    }
}
