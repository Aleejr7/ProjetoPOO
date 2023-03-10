package br.com.ada.crud.controller.pais;

import br.com.ada.crud.model.pais.Pais;

import java.util.List;
import java.util.UUID;

public interface PaisController {

    void cadastrar(Pais pais);

    Pais ler(UUID id);

    List<Pais> listar();

    void atualizar(UUID id, Pais pais);

    Pais excluir(UUID id);
}
