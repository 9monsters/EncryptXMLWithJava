package jiajar.lollipop.com;

import org.junit.Test;

public class XmlEncryptionTest {

    @Test
    public void test() throws Exception {
        EncryptionKey encryptKey = new EncryptionKey();

        XmlEncryption encrypter = new XmlEncryption();
        encrypter.encryptXmlDocument("xml/library.xml",
                "xml/encryptedDocument.xml",
                encryptKey.getKey());
        encryptKey.saveToFile("encryption.key");

        EncryptionKey decryptKey = new EncryptionKey();
        decryptKey.loadFromFile("encryption.key");
        XmlDecryption decrypter = new XmlDecryption();
        decrypter.decryptXmlDocument("xml/encryptedDocument.xml",
                "xml/originalDocument.xml",
                decryptKey.getKey());
    }
}
