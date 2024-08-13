package configuration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FileReaderXml {
    private String pathname = "src" + File.separator + "conf.xml";
    private Document document;
    private File path;

    protected FileReaderXml(String pathname) throws IOException, SAXException, ParserConfigurationException {
        initXML(pathname);
    }

    protected void initXML(String pathname) throws SAXException, IOException, ParserConfigurationException {
        if(!(pathname == null || pathname.equals(""))) this.pathname = pathname;
        path = new File(this.pathname);
        if (!path.exists()) throw new IOException(path.getAbsolutePath() + " : le pathname n'est pas valide.");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(path);
        document.getDocumentElement().normalize();
    }

    protected HashMap<String, ArrayList<HashMap<String, String>>> getElements() {
        HashMap<String, ArrayList<HashMap<String, String>>> elementsMap = new HashMap<>();
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        
        readElements(elementsMap, nodeList);

        return elementsMap;
    }

    private void readElements(HashMap<String, ArrayList<HashMap<String, String>>> elementsMap, NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNodeList = nodeList.item(i);
    
            if (childNodeList.getNodeType() == Node.ELEMENT_NODE) {
                HashMap<String, String> attributeMap = new HashMap<>();
                NamedNodeMap attributes = childNodeList.getAttributes();
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attribute = attributes.item(j);
                    attributeMap.put(attribute.getNodeName(), attribute.getNodeValue());
                }
    
                String fullNodeName = childNodeList.getParentNode().getNodeName() + "." + childNodeList.getNodeName();
    
                if (!elementsMap.containsKey(fullNodeName)) {
                    elementsMap.put(fullNodeName, new ArrayList<>());
                }
    
                elementsMap.get(fullNodeName).add(attributeMap);
    
                if (childNodeList.hasChildNodes()) {
                    readElements(elementsMap, childNodeList.getChildNodes());
                }
            }
        }
    }

    protected void printHashMap() {
        HashMap<String, ArrayList<HashMap<String, String>>> file = getElements();

        for (Object objectName : file.keySet()) {
            System.out.println("name=" + objectName + " value="+file.get(objectName));
        }
    }

    public static void printHashMap(String path) {
        try {
            FileReaderXml fileReader = new FileReaderXml(null);
            fileReader.printHashMap();

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}

