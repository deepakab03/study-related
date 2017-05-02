package com.deepak.questions.gedcom.bdd;

import static com.deepak.questions.gedcom.util.TestUtils.readDataFromTestFile;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.custommonkey.xmlunit.XMLTestCase;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.xml.sax.SAXException;

import com.deepak.questions.gedcom.parser.GenealogyDataParser;
import com.deepak.questions.gedcom.util.GenealogyDataExtractorUtils;
import com.deepak.questions.gedcom.xml.XmlCreator;

public class GenealogyDataExtractorSteps extends XMLTestCase {
    // where is my slf4j/log4j, sob..
    private static final Logger         logger      = Logger.getLogger(GenealogyDataExtractorSteps.class
                                                            .getName());

    private GenealogyDataExtractorUtils gedcomUtils = new GenealogyDataExtractorUtils();
    private GenealogyDataParser         parser      = new GenealogyDataParser();
    private XmlCreator                  xmlCreator  = new XmlCreator();

    private List<String>                dataList;
    private DefaultMutableTreeNode      dataTree;
    private String                      generatedXml;

    @Given("a Gedcom data file")
    public void aDataFlile() throws IOException {
        logger.info("Staring system test..");
        // TODO better to have a listener that calls the parser for each line parsed, to avoid problems with
        // large files
        dataList = gedcomUtils.readClassPathFile("/bdd/data/GedcomFileDataSample.txt");

        assertThat(dataList, is(notNullValue()));
    }

    @When("it is parsed")
    public void theDataFileIsParsed() throws InterruptedException {
        dataTree = parser.parse(dataList);

        assertThat(dataTree, is(notNullValue()));
        assertThat(dataTree.getUserObject(), is(equalTo((Object) "data")));
        
//        displayInSwingWindow(dataTree);
    }

    @Then("a correct xml output is generated")
    public void theXmlOutputIsGenerated() throws ParserConfigurationException, TransformerException,
            IOException, SAXException {
        generatedXml = xmlCreator.createXmlFromTree(dataTree);
        logger.info("Generated xml: " + generatedXml);
        
        String expectedXml = readDataFromTestFile("/bdd/data/expectedOutput.xml");
        assertThat(generatedXml, is(notNullValue()));
        assertXMLEqual("xml Not the same", expectedXml, generatedXml);
    }

    @Then("is stored in a file in the file-system")
    public void theXlmlIsStoredInTheFile() throws ParserConfigurationException, TransformerException,
            IOException, InterruptedException {
        String generatedXmlOutputPath = System.getProperty("generatedXmlOutputPath", "");
        generatedXmlOutputPath += File.separator + "output.xml";
        gedcomUtils.writeFileSystemFile(generatedXmlOutputPath, generatedXml);

        File file = new File(generatedXmlOutputPath);

        assertThat(file.exists(), is(TRUE));
        
//        displayInSwingWindow(dataTree);
        
    }
    
    private void displayInSwingWindow(DefaultMutableTreeNode tree) throws InterruptedException {
        TreeModel model = new DefaultTreeModel(tree);
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(new JTree(model)));
        frame.validate();
        frame.setSize(new Dimension(400, 400));
        frame.setVisible(true);

        Thread.sleep(200 * 1000);
    }

}
