package br.com.ada.crud.controller.pais.arquivo;

import br.com.ada.crud.controller.arquivo.AbstractArquivo;
import br.com.ada.crud.controller.arquivo.EscritorArquivo;
import br.com.ada.crud.controller.arquivo.LeitorArquivo;
import br.com.ada.crud.model.DAOException;
import br.com.ada.crud.model.pais.Pais;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaisArquivoBinario extends AbstractArquivo implements EscritorArquivo<Pais>, LeitorArquivo<Pais> {

    private static final String EXTENSAO = ".dat";

    private String diretorio = "database/binario/pais";

    public void escrever(Pais pais, String arquivo) {
        File file = new File(diretorio, arquivo + EXTENSAO);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(pais);
            objectOutputStream.flush();
        } catch (IOException ex) {
            throw new DAOException("Falha ao trabalhar os arquivos.", ex);
        }
    }
    public Pais apagar(String arquivo) {
        Pais pais = ler(arquivo);
        apagarArquivo(diretorio, arquivo + EXTENSAO);
        return pais;
    }

    public List<Pais> ler() {
        FilenameFilter filter = (dir, nome) -> nome.endsWith(EXTENSAO);

        List<Pais> paises = new ArrayList<>();
        File diretorio = new File(this.diretorio);

        for (File arquivo : diretorio.listFiles(filter)) {
            Pais pais = ler(arquivo);
            paises.add(pais);
        }
        return paises;
    }

    @Override
    public Pais ler(String arquivo) {
        File arquivoASerLido = new File(diretorio, arquivo + EXTENSAO);
        Pais pais = ler(arquivoASerLido);
        return pais;
    }

    private Pais ler(File arquivo) {
        try (FileInputStream fileInputStream = new FileInputStream(arquivo);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object = objectInputStream.readObject();
            if (object instanceof Pais) {
                return (Pais) object;
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new DAOException("Falha na leitura do arquivo " + arquivo.getAbsolutePath(), ex);
        }
    }
}
