package com.servifacil.SF_BackEnd.services;

import com.servifacil.SF_BackEnd.models.ServiceModel;
import com.servifacil.SF_BackEnd.repositories.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository repository;

    public ServiceService(ServiceRepository repository) {
        this.repository = repository;
    }

    public ServiceModel criarServico(ServiceModel servico) {
        return repository.save(servico);
    }

    public List<ServiceModel> listarServicos() {
        return repository.findAll();
    }

    public List<ServiceModel> buscarPorNome(String nome) {
        return repository.findByTitleContainingIgnoreCase(nome);
    }

    public List<ServiceModel> buscarPorCategoria(int categoriaId) {
        return repository.findByCategoryId(categoriaId);
    }

    public List<ServiceModel> buscarPorProfissional(int profissionalId) {
        return repository.findByProfessionalId(profissionalId);
    }

    public ServiceModel editarServico(int id, ServiceModel novoServico) {
        ServiceModel servico = repository.findById(id).orElseThrow();
        servico.setTitle(novoServico.getTitle());
        servico.setPrice(novoServico.getPrice());
        servico.setDetails(novoServico.getDetails());
        servico.setCategoryId(novoServico.getCategoryId());
        return repository.save(servico);
    }

    public void excluirServico(int id) {
        repository.deleteById(id);
    }
}
