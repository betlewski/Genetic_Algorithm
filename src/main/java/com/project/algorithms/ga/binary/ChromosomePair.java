package com.project.algorithms.ga.binary;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChromosomePair {

    private Chromosome chromosomeX;
    private Chromosome chromosomeY;

    public double getFunctionValue() {
        return SimpleGeneticAlgorithm.getFunctionValue(this);
    }

}
