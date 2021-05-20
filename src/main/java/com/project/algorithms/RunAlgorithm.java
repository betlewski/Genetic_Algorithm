package com.project.algorithms;

import com.project.algorithms.ga.binary.SimpleGeneticAlgorithm;

public class RunAlgorithm {

    public static void main(String[] args) {
        SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        ga.runAlgorithm(50, "1011000100000100010000100000100111001000000100000100000000001111");
    }

}
