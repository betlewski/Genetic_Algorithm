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

    protected Chromosome getChromosome(int index) {
        return chromosomes.get(index);
    }

    protected Chromosome getFittest() {
        Chromosome fittest = chromosomes.get(0);
        for (int i = 1; i < chromosomes.size(); i++) {
            if (getChromosome(i).getFitness() < fittest.getFitness()) {
                fittest = getChromosome(i);
            }
        }
        return fittest;
    }

    private void createNewPopulation(int size) {
        for (int i = 0; i < size; i++) {
            Chromosome newChromosome = new Chromosome();
            chromosomes.add(i, newChromosome);
        }
    }
}
