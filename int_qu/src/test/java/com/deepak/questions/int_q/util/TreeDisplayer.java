package com.deepak.questions.int_q.util;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.deepak.questions.int_q.util.tree.BTNode;
import com.deepak.questions.int_q.util.tree.BTree;

public class TreeDisplayer {

    public void display(BTree bTree) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(bTree.getRootNode());
        
        createNodes(top, bTree.getRootNode());

        JTree tree = new JTree(top);
//        tree.putClientProperty ("JTree.lineStyle", "Horizontal");
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
        JScrollPane pane = new JScrollPane(tree);
        JFrame frame = new JFrame("Tree displayer");
        frame.getContentPane().add(pane);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createNodes(DefaultMutableTreeNode top, BTNode node) {
        if (node.hasChildren()) {
            BTNode leftNode = node.getLeftNode();
            if (leftNode != null) {
                DefaultMutableTreeNode leftTreeComp = new DefaultMutableTreeNode(leftNode);
                top.add(leftTreeComp);
                createNodes(leftTreeComp, leftNode);
            }
            BTNode rightNode = node.getRightNode();
            if (rightNode != null) {
                DefaultMutableTreeNode rightTreeComp = new DefaultMutableTreeNode(rightNode);
                top.add(rightTreeComp);
                createNodes(rightTreeComp, rightNode);
            }
        }
    }
    
    public static void main(String[] args) {
        BTNode parent = new BTNode("A");
        BTNode leftNode = new BTNode("B");
        BTNode rightNode = new BTNode("C");
        
        BTNode dNode = new BTNode("D");
        leftNode.setLeftNode(dNode);
        leftNode.setRightNode(new BTNode("E"));
        dNode.setLeftNode(new BTNode("F"));
        dNode.setRightNode(new BTNode("G"));
        
        rightNode.setLeftNode(new BTNode("H", new BTNode("H1", null, new BTNode("H2", new BTNode("H3"), new BTNode("H4"))), null));
        rightNode.setRightNode(new BTNode("I"));
        
        parent.setLeftNode(leftNode);
        parent.setRightNode(rightNode);
        
        new TreeDisplayer().display(new BTree(parent));
    }
}
