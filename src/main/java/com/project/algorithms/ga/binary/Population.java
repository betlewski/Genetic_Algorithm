package com.project.algorithms.ga.binary;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Population {

    private List<ChromosomePair> chromosomePairs;

    public Population(int size, boolean createNew) {
        chromosomePairs = new ArrayList<>();
        if (createNew) {
            createNewPopulation(size);
        }
    }

    private void createNewPopulation(int size) {
        for (int i = 0; i < size; i++) {
            Chromosome chromosomeX = new Chromosome();
            Chromosome chromosomeY = new Chromosome();
            ChromosomePair chromosomePair = new ChromosomePair(chromosomeX, chromosomeY);
            chromosomePairs.add(i, chromosomePair);
        }
    }

    protected ChromosomePair getChromosomePair(int index) {
        return chromosomePairs.get(index);
    }

    protected ChromosomePair getBest() {
        ChromosomePair minimum = chromosomePairs.get(0);
        for (int i = 1; i < chromosomePairs.size(); i++) {
            if (getChromosomePair(i).getFunctionValue() < minimum.getFunctionValue()) {
                minimum = getChromosomePair(i);
            }
        }
        return minimum;
    }

}
