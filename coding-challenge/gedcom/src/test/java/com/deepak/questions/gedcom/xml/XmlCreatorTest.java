package com.deepak.questions.gedcom.xml;

import static com.deepak.questions.gedcom.util.TestUtils.readDataFromTestFile;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.custommonkey.xmlunit.XMLTestCase;
import org.xml.sax.SAXException;

import com.deepak.questions.gedcom.model.GenealogyNode;

public class XmlCreatorTest extends XMLTestCase {

    public void test_givenATreeOfGedcomData_shouldCreateMatchingXml() throws IOException, SAXException,
            ParserConfigurationException, TransformerException {
        final DefaultMutableTreeNode dataTree = aTreeWithGenealogyData();
        final XmlCreator xmlCreator = new XmlCreator();

        final String xml = xmlCreator.createXmlFromTree(dataTree);
        String expectedXml = readDataFromTestFile("/com/deepak/questions/gedcom/xml/expectedGenerationalXmlOutput.xml");
        assertThat(xml, is(notNullValue()));
        assertXMLEqual("xml not equal", xml, expectedXml);
    }

    private DefaultMutableTreeNode aTreeWithGenealogyData() {
        final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("data");

        final DefaultMutableTreeNode indiNode = aTreeGenealogyIdNode("INDI", "I0001");
        rootNode.add(indiNode);
        indiNode.add(aTreeGenealogyNode("NAME", "Elizabeth Alexandra Mary /Windsor/"));
        indiNode.add(aTreeGenealogyNode("SEX", "F"));
        final DefaultMutableTreeNode birthDetailsNode = aTreeGenealogyNode("BIRT", "");
        birthDetailsNode.add(aTreeGenealogyNode("DATE", "21 Apr 1926"));
        birthDetailsNode.add(aTreeGenealogyNode("PLAC", "17 Bruton Street, London, W1"));
        indiNode.add(birthDetailsNode);
        indiNode.add(aTreeGenealogyNode("OCCU", "Queen"));
        indiNode.add(aTreeGenealogyIdNode("FAMC", "F0003"));
        rootNode.add(aTreeGenealogyNode("TRLR", ""));

        return rootNode;
    }

    private DefaultMutableTreeNode aTreeGenealogyIdNode(String nodeName, String nodeValue) {
        return new DefaultMutableTreeNode(new GenealogyNode(nodeName, nodeValue));
    }

    private DefaultMutableTreeNode aTreeGenealogyNode(String nodeName, String nodeValue) {
        return new DefaultMutableTreeNode(new GenealogyNode(nodeName, nodeValue));
    }

}
