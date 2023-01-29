package br.com.ada.crud.model.estado.dao.impl;

import br.com.ada.crud.controller.arquivo.EscritorArquivo;
import br.com.ada.crud.controller.arquivo.LeitorArquivo;
import br.com.ada.crud.model.estado.Estado;
import br.com.ada.crud.model.estado.dao.EstadoDAO;

import java.util.List;
import java.util.UUID;

public class EstadoArquivoDAO implements EstadoDAO {

    private EscritorArquivo<Estado> escritor;
    private LeitorArquivo<Estado> leitor;

    public EstadoArquivoDAO(
            EscritorArquivo<Estado> escritor,
            LeitorArquivo<Estado> leitor
    ) {
        this.escritor = escritor;
        this.leitor = leitor;
    }

    @Override
    public void cadastrar(Estado estado) {
        escritor.escrever(estado, estado.getId().toString());
    }

    @Override
    public List<Estado> listar() {
        return leitor.ler();
    }

    @Override
    public Estado buscar(UUID id) {
        Estado estado = leitor.ler(id.toString());
        return estado;
    }

    @Override
    public void atualizar(UUID id, Estado estado) {
        escritor.escrever(estado, id.toString());
    }

    @Override
    public Estado apagar(UUID id) {
        return escritor.apagar(id.toString());
    }
}
