package com.project.algorithms;

import com.project.algorithms.ga.binary.FunctionType;
import org.junit.Assert;
import org.junit.Test;

import com.project.algorithms.ga.binary.SimpleGeneticAlgorithm;

public class BinaryGeneticAlgorithmLongRunningUnitTest {

    @Test
    public void testGA() {
        SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        Assert.assertTrue(ga.runAlgorithm(5, FunctionType.ABSOLUTE_X_MINUS_2));
    }

}
