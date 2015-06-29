package jiajar.lollipop.com;


import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.EncryptionConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.security.Key;


public class XmlDecryption {

    private Document document;

    public XmlDecryption() {
        org.apache.xml.security.Init.init();
    }

    public void decryptXmlDocument(String sourceFilePath,
                                   String decryptedFilePath,
                                   Key encryptionKey)
            throws Exception {
        document = DocumentFileMapper.load(sourceFilePath);
        decryptDocument(encryptionKey);
        DocumentFileMapper.save(document, decryptedFilePath);
    }

    private void decryptDocument(Key encryptionKey) throws Exception {
        XMLCipher cipher = XMLCipher.getInstance();
        cipher.init(XMLCipher.DECRYPT_MODE, null);
        cipher.setKEK(encryptionKey);
        cipher.doFinal(document, getEncryptedData());
    }

    private Element getEncryptedData() {
        String namespaceURI = EncryptionConstants.EncryptionSpecNS;
        String localName = EncryptionConstants._TAG_ENCRYPTEDDATA;
        return (Element) document.getElementsByTagNameNS(namespaceURI, localName).item(0);
    }
}
