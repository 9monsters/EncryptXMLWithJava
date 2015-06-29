package jiajar.lollipop.com;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

public class DocumentFileMapper {

    public static Document load(String filePath) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        DocumentBuilder builder = documentFactory.newDocumentBuilder();
        return builder.parse(filePath);
    }

    public static void save(Document document, String filePath) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(new File(filePath));

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer xmlTransformer = factory.newTransformer();
        xmlTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(outputStream);
        xmlTransformer.transform(source, result);

        outputStream.close();
    }
}
