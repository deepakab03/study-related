package com.deepak.questions.int_q.tdme;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class LargePackageSmallPackageTest {

    @Parameters({"16, 2, 10, 8",
        "4, 1, 4, 4",
        "4, 1, 5, 4",
        "5, 1, 5, 1",
        "5, 2, 5, 1",
        "10, 2, 15, 2",
        "10, 1, 5, 6",
        "17, 2, 10, 9",
        "17, 1, 12, 13",
        "17, 0, 17, 17",
        "17, 0, 20, 17",
        "17, 0, 16, -1",
        "20, 4, 0, 4",
        "11, 1, 5, -1",
        "18, 5, 0, -1",
        "18, 0, 17, -1",
        "18, 0, 0, -1",
        "18, 1, 12, -1",
        "22, 6, 1, -1",
        "63, 15, 2, -1"})
    @Test(timeout = 1000) public void 
    testScenarios(int numItems, int givenLargePkgs, int givenSmallPkgs, int expectedPkgs) {
        LargePackageSmallPackage package1 = new LargePackageSmallPackage();
        
        int returnedPkgs = package1.minimalNumPkges2(numItems, givenLargePkgs, givenSmallPkgs);
//        int returnedPkgs = package1.minimalNumberOfPackagesCh4(numItems, givenLargePkgs, givenSmallPkgs);
        
        assertThat(returnedPkgs, is(equalTo(expectedPkgs)));
    }
}
