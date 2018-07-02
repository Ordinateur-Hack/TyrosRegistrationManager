package com.yamaha.model.editor.Style;

        import org.w3c.dom.Document;
        import org.w3c.dom.Element;
        import org.w3c.dom.Node;
        import org.w3c.dom.NodeList;

        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.DocumentBuilderFactory;
        import java.io.File;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.LinkedList;
        import java.util.List;

public class StyleName {

    private final static String XML_NAME = "Tyros3_Styles.xml";
    private static Document doc;

    // see https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
    private static void readXMLFile() {
        try {
            File xmlFile = new File(StyleName.class.getResource(XML_NAME).toURI());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStyleName(String pos1, String pos2) {
        readXMLFile();
        NodeList posNodes = doc.getElementsByTagName("pos");
        for (int i = 0; i < posNodes.getLength(); i++) {
            if (posNodes.item(i).getTextContent().matches(pos1 + " " + pos2)) {
                Element styleElement = (Element) posNodes.item(i).getParentNode();
                Node nameNode = styleElement.getElementsByTagName("name").item(0);
                return nameNode.getTextContent();
            }
        }
        return "Not yet specified";
    }

    public static List<String> getPos(String styleName) {
        NodeList posNodes = doc.getElementsByTagName("name");
        for (int i = 0; i < posNodes.getLength(); i++) {
            if (posNodes.item(i).getTextContent().equals(styleName)) {
                Element styleElement = (Element) posNodes.item(i).getParentNode();
                Node posNode = styleElement.getElementsByTagName("pos").item(0);
                String pos = posNode.getTextContent();
                return Arrays.asList(pos.substring(0, 2), pos.substring(3, 5));
            }
        }
        return null;
    }

}
