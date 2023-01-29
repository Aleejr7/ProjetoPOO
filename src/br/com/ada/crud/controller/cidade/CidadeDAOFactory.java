package br.com.ada.crud.controller.cidade;

import br.com.ada.crud.Constantes;
import br.com.ada.crud.controller.arquivo.EscritorArquivo;
import br.com.ada.crud.controller.arquivo.LeitorArquivo;
import br.com.ada.crud.controller.arquivo.PersistenciaTipoArquivo;
import br.com.ada.crud.controller.cidade.arquivo.CidadeArquivoBinario;
import br.com.ada.crud.controller.cidade.arquivo.CidadeArquivoXml;
import br.com.ada.crud.model.cidade.Cidade;
import br.com.ada.crud.model.cidade.dao.CidadeDAO;
import br.com.ada.crud.model.cidade.dao.impl.CidadeArquivoDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CidadeDAOFactory {

    private static final CidadeDAOFactory INSTANCE = new CidadeDAOFactory();
    private static final String PERSISTENCIA_TIPO = "persistencia.tipo";

    private PersistenciaTipoArquivo tipo;

    private CidadeDAOFactory() {
    }

    public static CidadeDAOFactory getInstance() {
        return INSTANCE;
    }

    public CidadeDAO create() {
        if (tipo == null) {
            carregarTipoPersistencia();
        }
        EscritorArquivo<Cidade> escritor = null;
        LeitorArquivo<Cidade> leitor = null;

        if (tipo == PersistenciaTipoArquivo.BINARIA) {
            escritor = new CidadeArquivoBinario();
            leitor = new CidadeArquivoBinario();
        } else if (tipo == PersistenciaTipoArquivo.XML) {
            escritor = new CidadeArquivoXml();
            leitor = new CidadeArquivoXml();
        }
        return new CidadeArquivoDAO(
                escritor,
                leitor
        );
    }

    private void carregarTipoPersistencia() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(Constantes.ARQUIVO_PROPRIEDADES));

            String valorNoArquivo = properties.getProperty(PERSISTENCIA_TIPO);
            tipo = PersistenciaTipoArquivo.valueOf(valorNoArquivo);
        } catch (IOException ex) {
            throw new RuntimeException("Não foi possível ler o arquivo de configurações", ex);
        }
    }
}
