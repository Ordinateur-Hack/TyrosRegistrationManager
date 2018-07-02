package com.yamaha.model.editor.Style;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class StyleName {

    private final static String XML_NAME = "Tyros3_Styles.xml";
    private static Document doc;

    /**
     * Parses the xml-file specified by the constant {@link #XML_NAME}
     * and stores the outputted document in the variable {@link #doc} for later use.
     */
    private static void readXMLFile() {
        // see https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        try {
            InputStream is = StyleName.class.getResourceAsStream(XML_NAME);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(is);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does
            // -it-work
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the style name for the given pos1 and pos2.
     * <br>Note that "pos" stands for "position". This means the position of the style
     * in the database where all the style names are stored (see xml-document).
     *
     * @param pos1 hex representation of the first data byte in
     *             {@link com.yamaha.model.chunkFramework.GPmType#STANDARD_STYLE} (07)
     * @param pos2 hex representation of the second data byte in
     *             {@link com.yamaha.model.chunkFramework.GPmType#STANDARD_STYLE} (07)
     * @return the style name for the given pos1 and pos2
     */
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

    /**
     * Returns the pos1 and the pos2 for the style with the given name.
     * <br>These hex representations stand for the position of the style in the database
     * where all the style names are stored (see xml-document).
     *
     * @param styleName the name of the style
     * @return pos1 and pos2 stored in a list
     */
    public static List<String> getHexPositions(String styleName) {
        NodeList posNodes = doc.getElementsByTagName("name");
        for (int i = 0; i < posNodes.getLength(); i++) {
            if (posNodes.item(i).getTextContent().equals(styleName)) {
                Element styleElement = (Element) posNodes.item(i).getParentNode();
                Node posNode = styleElement.getElementsByTagName("pos").item(0);
                String pos = posNode.getTextContent();
                return Arrays.asList(pos.substring(0, 2), pos.substring(3, 5)); // stored in xml like this: "17 40"
            }
        }
        return Arrays.asList("00", "00");
    }

}
