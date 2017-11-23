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
        "18, 5, 0, -1",
        "18, 0, 17, -1",
        "18, 1, 12, -1",
        "18, 0, 0, -1"})
    @Test public void 
    testScenarios(int numItems, int givenLargePkgs, int givenSmallPkgs, int expectedPkgs) {
        LargePackageSmallPackage package1 = new LargePackageSmallPackage();
        
        int returnedPkgs = package1.minimalNumPkges(givenLargePkgs, givenSmallPkgs, numItems);
        
        assertThat(returnedPkgs, is(equalTo(expectedPkgs)));
    }
}
