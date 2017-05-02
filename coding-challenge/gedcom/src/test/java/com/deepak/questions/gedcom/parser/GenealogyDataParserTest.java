package com.deepak.questions.gedcom.parser;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.junit.Test;

import com.deepak.questions.gedcom.model.GenealogyIdNode;
import com.deepak.questions.gedcom.model.GenealogyNode;
import com.deepak.questions.gedcom.util.GenealogyDataExtractorUtils;

public class GenealogyDataParserTest {

    @Test
    public void givenAListOfGendcomData_shouldCreateTreeRepresentation() throws IOException,
            InterruptedException {
        String classPathFile = "/com/deepak/questions/gedcom/parser/gedcomFileData.txt";
        List<String> dataList = new GenealogyDataExtractorUtils().readClassPathFile(classPathFile);

        GenealogyDataParser parser = new GenealogyDataParser();
        DefaultMutableTreeNode tree = parser.parse(dataList);

        assertThat(tree, is(notNullValue()));
        // displayInSwingWindow(tree);
        assertThat(tree.getUserObject(), is(equalTo((Object) "data")));
        assertThatChildNodesHaveCorrecValues(tree);

    }

    private void assertThatChildNodesHaveCorrecValues(DefaultMutableTreeNode tree) {
        DefaultMutableTreeNode defaultMutableFirstChild = (DefaultMutableTreeNode) tree.getFirstChild();
        GenealogyIdNode firstChild = (GenealogyIdNode) defaultMutableFirstChild.getUserObject();

        assertThat(firstChild, is(equalTo(aGenealogyIdNodeWith("INDI", "I0001"))));
        assertThat(nodeOf(defaultMutableFirstChild, 0),
                is(aGenealogyNodeWith("NAME", "Elizabeth Alexandra Mary /Windsor/")));
        assertThat(nodeOf(defaultMutableFirstChild, 1), is(aGenealogyNodeWith("SEX", "F")));
        assertThat(nodeOf(defaultMutableFirstChild, 2), is(aGenealogyNodeWith("BIRT", "")));
        assertThat(nodeOf(defaultMutableFirstChild.getChildAt(2), 0),
                is(aGenealogyNodeWith("DATE", "21 Apr 1926")));
        assertThat(nodeOf(defaultMutableFirstChild.getChildAt(2), 1),
                is(aGenealogyNodeWith("PLAC", "17 Bruton Street, London, W1")));
        assertThat(nodeOf(defaultMutableFirstChild, 3), is(aGenealogyNodeWith("OCCU", "Queen")));
        assertThat((GenealogyIdNode) nodeOf(defaultMutableFirstChild, 4),
                is(aGenealogyIdNodeWith("FAMC", "F0003")));
    }

    private GenealogyNode nodeOf(TreeNode treeNode, int index) {
        return getGenealogyNodeFromSwingMutableNode(treeNode, index);
    }

    private GenealogyNode getGenealogyNodeFromSwingMutableNode(TreeNode treeNode, int index) {
        DefaultMutableTreeNode child = (DefaultMutableTreeNode) treeNode.getChildAt(index);
        return (GenealogyNode) child.getUserObject();
    }

    private GenealogyNode aGenealogyNodeWith(String name, String value) {
        return new GenealogyNode(name, value);
    }

    private GenealogyIdNode aGenealogyIdNodeWith(String name, String value) {
        return new GenealogyIdNode(name, value);
    }
}
