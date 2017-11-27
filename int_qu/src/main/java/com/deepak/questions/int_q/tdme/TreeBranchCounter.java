package com.deepak.questions.int_q.tdme;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A tree is an abstract data structure consisting of nodes. Each node has only parent node and zero or more child
 * nodes. Each tree has one special node, called a root, which has no parent node. A node is called a branch if it one
 * or more children.
 * <p>
 * A tree can be represented by an array P where P[i] is the parent of node i. For the root node r, P[r] equals -1.
 * <p>
 * Write a program that counts the number of branches in a given tree.
 * <p>
 * For example the tree represented by an the array {1,3,1,-1,3} has 5 nodes, 0 to 4 of which 2 nodes are branches (only
 * nodes 1 and 3 have children)
 * 
 * <pre>
 *           (3)
 *          /   \
 *        (1)   (4)
 *       /   \
 *      (0)  (2)
 * </pre>
 * 
 * @author Deepak Abraham
 */
public class TreeBranchCounter {
    private static final Logger logger = LoggerFactory.getLogger(TreeBranchCounter.class);
    
    public int numBranches(int[] tree) {
        Collection<Integer> parentNodes = new LinkedHashSet<>();
        for (int node = 0; node < tree.length; node++) {
            int parent = tree[node];
            if (parent == -1) {
                logger.info("Found root node {}", node);
                addParent(parentNodes, node);
            } else {
                addParent(parentNodes, parent);
            }
        }
        return parentNodes.size();
    }

    private void addParent(Collection<Integer> parentNodes, int parent) {
        parentNodes.add(parent);
    }

}
