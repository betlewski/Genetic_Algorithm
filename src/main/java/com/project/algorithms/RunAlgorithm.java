package com.project.algorithms;

import com.project.algorithms.ga.binary.FunctionType;
import com.project.algorithms.ga.binary.SimpleGeneticAlgorithm;

public class RunAlgorithm {

    public static void main(String[] args) {
        SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        ga.runAlgorithm(FunctionType.ACKLEYS, 5, 100, 0.5, 0.025);
    }

}
