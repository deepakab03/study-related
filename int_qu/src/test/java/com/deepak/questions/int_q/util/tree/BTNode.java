package com.deepak.questions.int_q.util.tree;

public class BTNode extends TreeNode {

    private BTNode parentNode;
    private BTNode leftNode;
    private BTNode rightNode;
    
    public BTNode(Object value) {
        super(value);
    }
    
    public BTNode(Object value,  BTNode leftNode, BTNode rightNode) {
        super(value);
        setLeftNode(leftNode);
        setRightNode(rightNode);
    }
    
    public BTNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(BTNode parentNode) {
        this.parentNode = parentNode;
    }
    
    public BTNode getLeftNode() {
        return leftNode;
    }

    public BTNode getRightNode() {
        return rightNode;
    }

    public boolean hasChildren() {
        return leftNode != null || rightNode != null;
    }

    public void setLeftNode(BTNode leftNode) {
        this.leftNode = leftNode;
        if (leftNode != null) {
            leftNode.setParentNode(this);
        }
    }

    public void setRightNode(BTNode rightNode) {
        this.rightNode = rightNode;
        if (rightNode != null) {
            rightNode.setParentNode(this);
        }
    }

    public void setChildNode(BTNode nodeObj) {
        if (getLeftNode() == null) {
            setLeftNode(nodeObj);
        } else  if (getRightNode() == null) {
            setRightNode(nodeObj);
        } else {
            throw new IllegalStateException("for Node " + getValue() + ", both left and right nodes filled, cannot add " + nodeObj);
        }
    }
}
