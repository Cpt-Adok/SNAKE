package tests;

import configuration.ConfigGame;

public class XmlReaderTest {
    public static void main(String[] args) {
        ConfigGame configXml = new ConfigGame(null);
        System.out.println(configXml.getMap());
    }
}
