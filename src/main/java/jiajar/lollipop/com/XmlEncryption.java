package jiajar.lollipop.com;


import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.keys.KeyInfo;
import org.w3c.dom.Document;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class XmlEncryption {

    private Document document;

    public XmlEncryption() {
        org.apache.xml.security.Init.init();
    }

    public void encryptXmlDocument(String sourceDocumentFilePath,
                                   String encryptedDocumentFilePath,
                                   Key encryptionKey)
            throws Exception {
        document = DocumentFileMapper.load(sourceDocumentFilePath);
        encryptDocument(encryptionKey);
        DocumentFileMapper.save(document, encryptedDocumentFilePath);
    }

    private void encryptDocument(Key encryptionKey) throws Exception {
        Key symmetricKey = generateSymmetricKey();
        EncryptedKey encryptedKey = getEncryptedKey(encryptionKey, symmetricKey);

        XMLCipher symmetricCipher = XMLCipher.getInstance(XMLCipher.AES_128);
        symmetricCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);

        EncryptedData encryptedData = symmetricCipher.getEncryptedData();
        KeyInfo info = new KeyInfo(document);
        info.add(encryptedKey);
        encryptedData.setKeyInfo(info);

        symmetricCipher.doFinal(document, document.getDocumentElement(), true);
    }

    private EncryptedKey getEncryptedKey(Key encryptionKey, Key symmetricKey) throws Exception {
        XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.TRIPLEDES_KeyWrap);
        keyCipher.init(XMLCipher.WRAP_MODE, encryptionKey);
        return keyCipher.encryptKey(document, symmetricKey);
    }


    private Key generateSymmetricKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        return generator.generateKey();
    }
}