package br.com.ada.crud.controller.arquivo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AbstractXmlArquivo extends AbstractArquivo {

    public void escreverArquivo(String diretorio, String nome, Document document) {
        File arquivoASerEscrito = new File(diretorio, nome);
        try (FileOutputStream output = new FileOutputStream(arquivoASerEscrito)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(output);

            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    protected void adicionarElemento(Document document, String nomeElemento, String valorElemento, Node noPai) {
        Element element = document.createElement(nomeElemento);
        element.setTextContent(valorElemento);
        noPai.appendChild(element);
    }

    protected Document criarNovoDocumento() throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();

        return builder.newDocument();
    }

    public Document parse(File arquivo) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(arquivo);
    }

    protected String getValorElemento(String nomeElemento, Element elementoPai) {
        Node node = elementoPai.getElementsByTagName(nomeElemento).item(0);
        return node.getTextContent();
    }
}
