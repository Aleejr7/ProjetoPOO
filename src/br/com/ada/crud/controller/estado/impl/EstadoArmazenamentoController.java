package br.com.ada.crud.controller.estado.impl;

import br.com.ada.crud.controller.estado.EstadoController;
import br.com.ada.crud.model.estado.Estado;
import br.com.ada.crud.model.estado.dao.EstadoDAO;

import java.util.List;
import java.util.UUID;

public class EstadoArmazenamentoController implements EstadoController {

    private EstadoDAO estadoDAO;

    public EstadoArmazenamentoController(
            EstadoDAO estadoDAO
    ) {
        this.estadoDAO = estadoDAO;
    }

    @Override
    public void cadastrar(Estado estado) {
        estado.setId(UUID.randomUUID());
        estadoDAO.cadastrar(estado);
    }

    @Override
    public Estado ler(UUID id) {
        return estadoDAO.buscar(id);
    }

    @Override
    public List<Estado> listar() {
        return estadoDAO.listar();
    }

    @Override
    public void atualizar(UUID id, Estado estado) {
        estadoDAO.atualizar(id, estado);
    }

    @Override
    public Estado excluir(UUID id) {
        return estadoDAO.apagar(id);
    }
}
