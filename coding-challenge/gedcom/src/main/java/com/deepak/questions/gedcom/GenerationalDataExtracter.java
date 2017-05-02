package com.deepak.questions.gedcom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.deepak.questions.gedcom.parser.GenealogyDataParser;
import com.deepak.questions.gedcom.util.GenealogyDataExtractorUtils;
import com.deepak.questions.gedcom.xml.XmlCreator;

/**
 * To Run from maven use the command below:
 * <p>
 * mvn -e exec:java -Dexec.mainClass=com.deepak.questions.gedcom.GenerationalDataExtracter -Dexec.args="E:\tech\eclipse-ws\questions\gedcom\data-for-inspection\GEDCOM-Parser-Challenge-sample-data.txt E:\tech\eclipse-ws\questions\gedcom\data-for-inspection\outData.xml"

 * @author deepak
 */
public class GenerationalDataExtracter {
    private static final String               USAGE_STRING = "2 arguments to main need to be specified. First the Input Genealogical Data Communication file path and the "
                                                                   + "second the output file path where generated xml will be stored";
    private final GenealogyDataExtractorUtils gedcomUtils  = new GenealogyDataExtractorUtils();
    private final GenealogyDataParser         parser       = new GenealogyDataParser();
    private final XmlCreator                  xmlCreator   = new XmlCreator();

    private final String                      inputFilePath;
    private final String                      generatedXmlOutputPath;

    public GenerationalDataExtracter(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(USAGE_STRING);
        }
        inputFilePath = args[0];
        generatedXmlOutputPath = args[1];
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException,
            TransformerException {

        new GenerationalDataExtracter(args).extractData();
    }

    private void extractData() throws IOException, ParserConfigurationException, TransformerException {
        List<String> dataList = gedcomUtils.readFileSystemFile(inputFilePath);
        DefaultMutableTreeNode dataTree = parser.parse(dataList);
        String generatedXml = xmlCreator.createXmlFromTree(dataTree);

        String generatedXmlOutputPathWithFileName = constructOutputFilePath();

        gedcomUtils.writeFileSystemFile(generatedXmlOutputPathWithFileName, generatedXml);

    }

    private String constructOutputFilePath() {
        String generatedXmlOutputPathWithFileName = generatedXmlOutputPath;
        if (!generatedXmlOutputPath.endsWith(".xml")) {
            generatedXmlOutputPathWithFileName += File.separator + "output.xml";
        }
        return generatedXmlOutputPathWithFileName;
    }

}
