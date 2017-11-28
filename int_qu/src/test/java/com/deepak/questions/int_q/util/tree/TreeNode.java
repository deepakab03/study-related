package com.deepak.questions.int_q.util.tree;

public class TreeNode {

    private final Object value;

    public TreeNode(Object value) {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        return value == null ? "": String.valueOf(value);
    }
    
    public Object getValue() {
        return value;
    };
    
}
