package br.com.ada.crud.controller.arquivo;

public interface EscritorArquivo<T> {

    void escrever(T object, String arquivo);

    T apagar(String arquivo);
}
