package com.deepak.questions.int_q.tdme;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.deepak.questions.int_q.util.TreeDisplayer;
import com.deepak.questions.int_q.util.tree.BTNode;
import com.deepak.questions.int_q.util.tree.BTree;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class TreeBranchCounterTest {

    private TreeBranchCounter branchCounter = new TreeBranchCounter();
    
    @Test public void
    whenNumBranches_givenSampleTree_shouldReturnCorrectNumBranches() {
        int tree[] = {1,3,1,-1,3};
        
        int branches = branchCounter.numBranches(tree);
        
        assertThat(branches, is(equalTo(2)));
    }
    
    @Parameters(method = "testData")
    @Test public void
    whenNumBranches_givenOtherTreeTree_shouldReturnCorrectNumBranches(int[] tree, int expectedNumBranches) {
        int branches = branchCounter.numBranches(tree);

        assertThat(branches, is(equalTo(expectedNumBranches)));
    }
    
    @SuppressWarnings("unused")
    private Object[][] testData() {
        return new Object[][] {
                /**
                 * <pre>
                  *                                     (11)
                  *                                   /      \
                  *                                 /          \
                  *                               /              \
                  *                             (5)               (12)
                  *                            /    \             /    \
                  *                           /       \          /      \
                  *                         (3)        (6)      (13)    (14)
                  *                        /   \      /  \
                  *                      (1)   (4)  (7)   (10)
                  *                     /  \        /  \
                  *                    (0)  (2)   (8)   (9)
                 * </pre>
                 */
                new Object[] {new int[] {1,3,1,5,3,11,5,6,7,7,6,-1,11,12,12}, 7}
        };
    }
    
    public static void main(String[] args) {
        TreeBranchCounterTest test = new TreeBranchCounterTest();
        Object[][] data  = test.testData();
        int[] tree = (int[]) data[0][0]; //{1,3,1,-1,3}; 
        BTNode rootNode = null;
        Map<Integer, BTNode> nodeToNodeObjMap = new HashMap<>();
        for (int node = 0 ; node < tree.length; node++) {
            BTNode nodeObj = null;
            if ( (nodeObj = nodeToNodeObjMap.get(node)) == null) {
                nodeObj= new BTNode(node);
                nodeToNodeObjMap.put(node, nodeObj);
            } 
            
            int parentNode = tree[node];
            if (parentNode == -1) {
                rootNode = nodeObj;
                nodeToNodeObjMap.put(-1, nodeObj);
                continue;
            }
            BTNode parentNodeObj = null;
            if ((parentNodeObj = nodeToNodeObjMap.get(parentNode)) == null) {
                parentNodeObj = new BTNode(parentNode);
                nodeToNodeObjMap.put(parentNode, parentNodeObj);
            }
            parentNodeObj.setChildNode(nodeObj);
        }
        new TreeDisplayer().display(new BTree(rootNode));
    }
}
