package br.com.ada.crud.controller.estado.arquivo;

import br.com.ada.crud.controller.arquivo.AbstractArquivo;
import br.com.ada.crud.controller.arquivo.EscritorArquivo;
import br.com.ada.crud.controller.arquivo.LeitorArquivo;
import br.com.ada.crud.model.DAOException;
import br.com.ada.crud.model.estado.Estado;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoArquivoBinario extends AbstractArquivo implements EscritorArquivo<Estado>, LeitorArquivo<Estado> {

    private static final String EXTENSAO = ".dat";

    private String diretorio = "database/binario/estado";

    public void escrever(Estado estado, String arquivo) {
        File file = new File(diretorio, arquivo + EXTENSAO);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(estado);
            objectOutputStream.flush();
        } catch (IOException ex) {
            throw new DAOException("Falha ao trabalhar os arquivos.", ex);
        }
    }
    public Estado apagar(String arquivo) {
        Estado estado = ler(arquivo);
        apagarArquivo(diretorio, arquivo + EXTENSAO);
        return estado;
    }

    public List<Estado> ler() {
        FilenameFilter filter = (dir, nome) -> nome.endsWith(EXTENSAO);

        List<Estado> estados = new ArrayList<>();
        File diretorio = new File(this.diretorio);

        for (File arquivo : diretorio.listFiles(filter)) {
            Estado estado = ler(arquivo);
            estados.add(estado);
        }
        return estados;
    }

    @Override
    public Estado ler(String arquivo) {
        File arquivoASerLido = new File(diretorio, arquivo + EXTENSAO);
        Estado estado = ler(arquivoASerLido);
        return estado;
    }

    private Estado ler(File arquivo) {
        try (FileInputStream fileInputStream = new FileInputStream(arquivo);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object = objectInputStream.readObject();
            if (object instanceof Estado) {
                return (Estado) object;
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new DAOException("Falha na leitura do arquivo " + arquivo.getAbsolutePath(), ex);
        }
    }
}
