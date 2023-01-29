package br.com.ada.crud.model.cidade.dao.impl;

import br.com.ada.crud.controller.arquivo.EscritorArquivo;
import br.com.ada.crud.controller.arquivo.LeitorArquivo;
import br.com.ada.crud.model.cidade.Cidade;
import br.com.ada.crud.model.cidade.dao.CidadeDAO;

import java.util.List;
import java.util.UUID;

public class CidadeArquivoDAO implements CidadeDAO {

    private EscritorArquivo<Cidade> escritor;
    private LeitorArquivo<Cidade> leitor;

    public CidadeArquivoDAO(
            EscritorArquivo<Cidade> escritor,
            LeitorArquivo<Cidade> leitor
    ) {
        this.escritor = escritor;
        this.leitor = leitor;
    }

    @Override
    public void cadastrar(Cidade cidade) {
        escritor.escrever(cidade, cidade.getId().toString());
    }

    @Override
    public List<Cidade> listar() {
        return leitor.ler();
    }

    @Override
    public Cidade buscar(UUID id) {
        Cidade cidade = leitor.ler(id.toString());
        return cidade;
    }

    @Override
    public void atualizar(UUID id, Cidade cidade) {
        escritor.escrever(cidade, id.toString());
    }

    @Override
    public Cidade apagar(UUID id) {
        return escritor.apagar(id.toString());
    }
}
