package br.com.ada.crud.controller.cidade.arquivo;

import br.com.ada.crud.controller.arquivo.AbstractArquivo;
import br.com.ada.crud.controller.arquivo.EscritorArquivo;
import br.com.ada.crud.controller.arquivo.LeitorArquivo;
import br.com.ada.crud.model.DAOException;
import br.com.ada.crud.model.cidade.Cidade;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CidadeArquivoBinario extends AbstractArquivo implements EscritorArquivo<Cidade>, LeitorArquivo<Cidade> {

    private static final String EXTENSAO = ".dat";

    private String diretorio = "database/binario/cidade";

    public void escrever(Cidade cidade, String arquivo) {
        File file = new File(diretorio, arquivo + EXTENSAO);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(cidade);
            objectOutputStream.flush();
        } catch (IOException ex) {
            throw new DAOException("Falha ao trabalhar os arquivos.", ex);
        }
    }
    public Cidade apagar(String arquivo) {
        Cidade cidade = ler(arquivo);
        apagarArquivo(diretorio, arquivo + EXTENSAO);
        return cidade;
    }

    public List<Cidade> ler() {
        FilenameFilter filter = (dir, nome) -> nome.endsWith(EXTENSAO);

        List<Cidade> cidades = new ArrayList<>();
        File diretorio = new File(this.diretorio);

        for (File arquivo : diretorio.listFiles(filter)) {
            Cidade cidade = ler(arquivo);
            cidades.add(cidade);
        }
        return cidades;
    }

    @Override
    public Cidade ler(String arquivo) {
        File arquivoASerLido = new File(diretorio, arquivo + EXTENSAO);
        Cidade cidade = ler(arquivoASerLido);
        return cidade;
    }

    private Cidade ler(File arquivo) {
        try (FileInputStream fileInputStream = new FileInputStream(arquivo);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object = objectInputStream.readObject();
            if (object instanceof Cidade) {
                return (Cidade) object;
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new DAOException("Falha na leitura do arquivo " + arquivo.getAbsolutePath(), ex);
        }
    }


}
