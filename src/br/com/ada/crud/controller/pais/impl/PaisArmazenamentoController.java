package br.com.ada.crud.controller.pais.impl;

import br.com.ada.crud.controller.pais.PaisController;
import br.com.ada.crud.model.pais.Pais;
import br.com.ada.crud.model.pais.dao.PaisDAO;

import java.util.List;
import java.util.UUID;

public class PaisArmazenamentoController implements PaisController {

    private PaisDAO paisDAO;

    public PaisArmazenamentoController(
            PaisDAO paisDAO
    ) {
        this.paisDAO = paisDAO;
    }

    @Override
    public void cadastrar(Pais pais) {
        pais.setId(UUID.randomUUID());
        paisDAO.cadastrar(pais);
    }

    @Override
    public Pais ler(UUID id) {
        return paisDAO.buscar(id);
    }

    @Override
    public List<Pais> listar() {
        return paisDAO.listar();
    }

    @Override
    public void atualizar(UUID id, Pais pais) {
        paisDAO.atualizar(id, pais);
    }

    @Override
    public Pais excluir(UUID id) {
        return paisDAO.apagar(id);
    }
}
