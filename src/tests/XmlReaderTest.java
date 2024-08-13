package tests;

import configuration.ConfigXml;
import configuration.FileReaderXml;

public class XmlReaderTest {
    public static void main(String[] args) {
        ConfigXml configXml = new ConfigXml(null);
        System.out.println(configXml.getCharacters());
    }
}
