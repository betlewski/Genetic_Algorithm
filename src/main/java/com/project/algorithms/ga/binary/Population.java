package com.project.algorithms.ga.binary;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Population {

    private List<Chromosome> chromosomes;

    public Population(int size, boolean createNew) {
        chromosomes = new ArrayList<>();
        if (createNew) {
            createNewPopulation(size);
        }
    }

    private void createNewPopulation(int size) {
        for (int i = 0; i < size; i++) {
            Chromosome newChromosome = new Chromosome();
            chromosomes.add(i, newChromosome);
        }
    }

    protected Chromosome getChromosome(int index) {
        return chromosomes.get(index);
    }

    protected Chromosome getBest() {
        Chromosome minimum = chromosomes.get(0);
        for (int i = 1; i < chromosomes.size(); i++) {
            if (getChromosome(i).getFunctionValue() < minimum.getFunctionValue()) {
                minimum = getChromosome(i);
            }
        }
        return minimum;
    }

}
