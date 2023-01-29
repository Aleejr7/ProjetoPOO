package br.com.ada.crud.controller.arquivo;

import java.util.List;

public interface LeitorArquivo<T> {

    T ler(String arquivo);

    List<T> ler();
}
