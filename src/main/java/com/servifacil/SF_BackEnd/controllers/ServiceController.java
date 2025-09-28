package com.servifacil.SF_BackEnd.controllers;

import com.servifacil.SF_BackEnd.models.ServiceModel;
import com.servifacil.SF_BackEnd.services.ServiceService;
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
    public ServiceModel criar(@RequestBody ServiceModel service) {
        return service.criarServico(service);
    }

    @GetMapping("/listar")
    public List<ServiceModel> listar() {
        return service.listarServicos();
    }

    @GetMapping("/buscar/nome/{nome}")
    public List<ServiceModel> buscarPorNome(@PathVariable String name) {
        return service.buscarPorNome(name);
    }

    @GetMapping("/buscar/categoria/{id}")
    public List<ServiceModel> buscarPorCategoria(@PathVariable int id) {
        return service.buscarPorCategoria(id);
    }

    @PutMapping("/editar/{id}")
    public ServiceModel editar(@PathVariable int id, @RequestBody ServiceModel service) {
        return service.editarServico(id, service);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable int id) {
        service.excluirServico(id);
    }
}
