package com.deepak.questions.gedcom.parser;

import static java.lang.String.format;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;

import com.deepak.questions.gedcom.exception.GenealogyParserException;
import com.deepak.questions.gedcom.model.GenealogyIdNode;
import com.deepak.questions.gedcom.model.GenealogyNode;

public class GenealogyDataParser {
    private static final Logger    logger       = Logger.getLogger(GenealogyDataParser.class.getName());
    // "possibleSpace node_number node possibleNodeValue possibleSpace" all separated by 0Or1OrMoreSpaces
    private static final Pattern   LINE_PATTERN = Pattern.compile(" *([0-9]+) *([A-Za-z0-9@]*) *(.*) *");
    private int                    nodeNumber;
    private String                 nodeName;
    private String                 nodeValue;

    private DefaultMutableTreeNode rootNode;
    private DefaultMutableTreeNode nodeToAddChildTo;

    public DefaultMutableTreeNode parse(List<String> dataList) {
        rootNode = new DefaultMutableTreeNode("data");

        logger.info("Parsing data containg lines: " + dataList.size());
        for (String line : dataList) {
            if ("".equals(line)) {
                continue;
            }
            parse(line);
            addToTree();
        }
        logger.info("After parsing obtained tree with size: " + rootNode.getChildCount());
        return rootNode;
    }

    private void parse(String line) {
        Matcher matcher = LINE_PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new GenealogyParserException(format("line '%s' doesn't match pattern %s", line,
                    LINE_PATTERN.toString()));
        }
        nodeNumber = Integer.parseInt(matcher.group(1));
        nodeName = matcher.group(2);
        nodeValue = matcher.group(3);

        if (logger.isLoggable(Level.FINE)) {
            logger.info(format("Parsed and obtained '%d' node, name '%s, value '%s'", nodeNumber, nodeName,
                    nodeValue));
        }
    }

    private void addToTree() {
        DefaultMutableTreeNode newNode = createGenealogyNode();
        if (nodeNumber == 0) {
            rootNode.add(newNode);
            nodeToAddChildTo = newNode;
        } else if (nodeNumber == 1) {
            nodeToAddChildTo.add(newNode);
        } else if (nodeNumber == 2) {
            // add sub child to the last nodes added
            int lastNodeIndex = nodeToAddChildTo.getChildCount() - 1;
            DefaultMutableTreeNode subChildNode = (DefaultMutableTreeNode) nodeToAddChildTo
                    .getChildAt(lastNodeIndex);
            subChildNode.add(newNode);
        }
    }

    private DefaultMutableTreeNode createGenealogyNode() {
        if (isThisNodeAndId(nodeName)) {// individual
            // NOTE we swap name and values here to be consistent
            return new DefaultMutableTreeNode(
                    new GenealogyIdNode(nodeValue, getIdByStrippingAtChar(nodeName)));
        } else if (isThisNodeAndId(nodeValue)) {// family?
            return new DefaultMutableTreeNode(
                    new GenealogyIdNode(nodeName, getIdByStrippingAtChar(nodeValue)));
        }
        return new DefaultMutableTreeNode(new GenealogyNode(nodeName, nodeValue));
    }

    private boolean isThisNodeAndId(String nodeAttribute) {
        return nodeAttribute.startsWith("@");
    }

    private String getIdByStrippingAtChar(String input) {
        return input.substring(1, input.length() - 1);
    }

}
