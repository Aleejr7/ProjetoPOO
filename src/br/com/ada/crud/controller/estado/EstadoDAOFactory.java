package br.com.ada.crud.controller.estado;

import br.com.ada.crud.Constantes;
import br.com.ada.crud.controller.arquivo.EscritorArquivo;
import br.com.ada.crud.controller.arquivo.LeitorArquivo;
import br.com.ada.crud.controller.arquivo.PersistenciaTipoArquivo;
import br.com.ada.crud.controller.estado.arquivo.EstadoArquivoBinario;
import br.com.ada.crud.controller.estado.arquivo.EstadoArquivoXml;
import br.com.ada.crud.model.estado.Estado;
import br.com.ada.crud.model.estado.dao.EstadoDAO;
import br.com.ada.crud.model.estado.dao.impl.EstadoArquivoDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EstadoDAOFactory {

    private static final EstadoDAOFactory INSTANCE = new EstadoDAOFactory();
    private static final String PERSISTENCIA_TIPO = "persistencia.tipo";

    private PersistenciaTipoArquivo tipo;

    private EstadoDAOFactory() {
    }

    public static EstadoDAOFactory getInstance() {
        return INSTANCE;
    }

    public EstadoDAO create() {
        if (tipo == null) {
            carregarTipoPersistencia();
        }
        EscritorArquivo<Estado> escritor = null;
        LeitorArquivo<Estado> leitor = null;

        if (tipo == PersistenciaTipoArquivo.BINARIA) {
            escritor = new EstadoArquivoBinario();
            leitor = new EstadoArquivoBinario();
        } else if (tipo == PersistenciaTipoArquivo.XML) {
            escritor = new EstadoArquivoXml();
            leitor = new EstadoArquivoXml();
        }
        return new EstadoArquivoDAO(
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
