package br.com.ada.crud.controller.arquivo;

import java.io.File;

public abstract class AbstractArquivo {

    public void criarDiretorio(String diretorio) {
        File file = new File(diretorio);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void apagarArquivo(String diretorio, String nome) {
        File arquivo = new File(diretorio, nome);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}
