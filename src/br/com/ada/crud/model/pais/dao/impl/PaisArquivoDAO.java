package br.com.ada.crud.model.pais.dao.impl;

import br.com.ada.crud.controller.arquivo.EscritorArquivo;
import br.com.ada.crud.controller.arquivo.LeitorArquivo;
import br.com.ada.crud.model.pais.Pais;
import br.com.ada.crud.model.pais.dao.PaisDAO;

import java.util.List;
import java.util.UUID;

public class PaisArquivoDAO implements PaisDAO {

    private EscritorArquivo<Pais> escritor;
    private LeitorArquivo<Pais> leitor;

    public PaisArquivoDAO(
            EscritorArquivo<Pais> escritor,
            LeitorArquivo<Pais> leitor
    ) {
        this.escritor = escritor;
        this.leitor = leitor;
    }

    @Override
    public void cadastrar(Pais pais) {
        escritor.escrever(pais, pais.getId().toString());
    }

    @Override
    public List<Pais> listar() {
        return leitor.ler();
    }

    @Override
    public Pais buscar(UUID id) {
        Pais pais = leitor.ler(id.toString());
        return pais;
    }

    @Override
    public void atualizar(UUID id, Pais pais) {
        escritor.escrever(pais, id.toString());
    }

    @Override
    public Pais apagar(UUID id) {
        return escritor.apagar(id.toString());
    }
}
