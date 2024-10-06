package com.example.Launcher;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class PlistModifier {

    public static void modifyPlist(String filePath) {
        try {
            // Load the XML document
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            // Find the key "SUEnableAutomaticChecks"
            NodeList keyNodes = doc.getElementsByTagName("key");
            for (int i = 0; i < keyNodes.getLength(); i++) {
                Node keyNode = keyNodes.item(i);
                if (keyNode.getTextContent().equals("SUEnableAutomaticChecks")) {
                    // Find the value node (should be right after the key node)
                    Node valueNode = keyNode.getNextSibling().getNextSibling(); // skipping the <text>

                    if (valueNode != null && valueNode.getNodeName().equals("true")) {
                        // Modify "true" to "false"
                        Element falseElement = doc.createElement("false");
                        keyNode.getParentNode().replaceChild(falseElement, valueNode);
                        System.out.println("Modified SUEnableAutomaticChecks to false");
                    }
                    break;
                }
            }

            // Write the updated document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Plist updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}