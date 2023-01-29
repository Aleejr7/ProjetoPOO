package br.com.ada.crud.controller.cidade.arquivo;

import br.com.ada.crud.controller.arquivo.AbstractXmlArquivo;
import br.com.ada.crud.controller.arquivo.EscritorArquivo;
import br.com.ada.crud.controller.arquivo.LeitorArquivo;
import br.com.ada.crud.model.cidade.Cidade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CidadeArquivoXml extends AbstractXmlArquivo implements EscritorArquivo<Cidade>, LeitorArquivo<Cidade> {

    public static final String EXTENSAO = ".xml";

    private String diretorio = "database/xml/cidade";

    public void escrever(Cidade cidade, String arquivo) {
        try {
            Document document = criarNovoDocumento();
            Element elementCidade = document.createElement("cidade");
            document.appendChild(elementCidade);

            adicionarElemento(document, "id", cidade.getId().toString(), elementCidade);
            adicionarElemento(document, "nome", cidade.getNome(), elementCidade);
            adicionarElemento(document, "estado", cidade.getEstado(), elementCidade);

            escreverArquivo(diretorio, arquivo + EXTENSAO, document);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cidade apagar(String arquivo) {
        Cidade cidade = ler(arquivo);
        apagarArquivo(diretorio, arquivo + EXTENSAO);
        return cidade;
    }

    @Override
    public Cidade ler(String arquivo) {
        File arquivoASerLido = new File(diretorio, arquivo + EXTENSAO);
        return ler(arquivoASerLido);
    }

    @Override
    public List<Cidade> ler() {
        FilenameFilter filter = (dir, nomeDoArquivo) -> nomeDoArquivo.endsWith(EXTENSAO);

        List<Cidade> cidades = new ArrayList<>();
        File diretorioQueContemOsArquvios = new File(diretorio);
        for (File arquivoASerLido : diretorioQueContemOsArquvios.listFiles(filter)) {
            Cidade cidade = ler(arquivoASerLido);
            cidades.add(cidade);
        }
        return cidades;
    }

    private Cidade ler(File arquivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(arquivo);
            Element elementCidade = document.getDocumentElement();
            Node elementId = elementCidade.getElementsByTagName("id").item(0);
            Node elementNome = elementCidade.getElementsByTagName("nome").item(0);
            Node elementEstado = elementCidade.getElementsByTagName("estado").item(0);

            Cidade cidade = new Cidade();
            cidade.setId(UUID.fromString(elementId.getTextContent()));
            cidade.setNome(elementNome.getTextContent());
            cidade.setEstado(elementEstado.getTextContent());
            return cidade;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
