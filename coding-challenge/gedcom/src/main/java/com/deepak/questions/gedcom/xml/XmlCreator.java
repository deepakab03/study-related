package com.deepak.questions.gedcom.xml;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.deepak.questions.gedcom.model.GenealogyNode;

public class XmlCreator {

    private static final String       DEFAULT_ATTRIBUTE_NAME        = "value";
    private static final Logger       logger                        = Logger.getLogger(XmlCreator.class
                                                                            .getName());
    private Document                  document;
    private final Map<String, String> specialTagToAttributeValueMap = Collections.singletonMap("indi", "id");

    public String createXmlFromTree(DefaultMutableTreeNode dataTree) throws ParserConfigurationException,
            TransformerException {
        String xml = null;
        createDocument();

        final Element rootDocumentElement = document.createElement(getRootTag(dataTree));
        addNodesToElement(dataTree, rootDocumentElement);
        document.appendChild(rootDocumentElement);
        xml = obtainXmlStringFromDocument();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Xml created: " + xml);
        }

        return xml;
    }

    private void addNodesToElement(DefaultMutableTreeNode dataTree, final Node rootDocumentElement) {
        for (int i = 0; i < dataTree.getChildCount(); i++) {
            final DefaultMutableTreeNode childMutableTreeNode = (DefaultMutableTreeNode) dataTree
                    .getChildAt(i);
            Node documentChildNode = createDocumentChildNode(childMutableTreeNode);
            rootDocumentElement.appendChild(documentChildNode);
            if (childMutableTreeNode.isLeaf()) {
                continue;
            }
            addNodesToElement(childMutableTreeNode, documentChildNode);
        }
    }

    private void createDocument() throws ParserConfigurationException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
    }

    public String getRootTag(DefaultMutableTreeNode dataTree) {
        return dataTree.getUserObject().toString();
    }

    private Node createDocumentChildNode(DefaultMutableTreeNode child) {
        GenealogyNode genealogyNode = (GenealogyNode) child.getUserObject();
        return createChild(genealogyNode.getNodeName(), genealogyNode.getNodeValue());
    }

    public Element createChild(String tag, String attributeValue) {
        final String lowerCaseTag = tag.toLowerCase();
        final Element element = document.createElement(lowerCaseTag);

        if (!"".equals(attributeValue)) {
            String attributeName = DEFAULT_ATTRIBUTE_NAME;
            if (specialTagToAttributeValueMap.containsKey(lowerCaseTag)) {
                attributeName = specialTagToAttributeValueMap.get(lowerCaseTag);
            }
            final Attr attributeAttr = document.createAttribute(attributeName);
            attributeAttr.setNodeValue(attributeValue);
            element.setAttributeNode(attributeAttr);
        }

        return element;
    }

    private String obtainXmlStringFromDocument() throws TransformerFactoryConfigurationError,
            TransformerConfigurationException, TransformerException {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        final DOMSource xmlSource = new DOMSource(document);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final StreamResult streamResult = new StreamResult(byteArrayOutputStream);

        transformer.transform(xmlSource, streamResult);

        return byteArrayOutputStream.toString();
    }

}
