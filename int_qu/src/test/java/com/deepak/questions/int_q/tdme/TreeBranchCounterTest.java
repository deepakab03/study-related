package com.deepak.questions.int_q.tdme;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

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
    private Object[] testData() {
        return new Object[] {
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
    
    
}
