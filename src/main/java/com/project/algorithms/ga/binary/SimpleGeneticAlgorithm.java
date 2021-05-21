package com.project.algorithms.ga.binary;

import lombok.Data;

@Data
public class SimpleGeneticAlgorithm {

    private static FunctionType functionType;
    private static int generationNumber;
    private static double mutationRate;

    private static final double uniformRate = 0.5;
    private static final int tournamentSize = 5;

    public boolean runAlgorithm(FunctionType function, int populationSize, int generations, double mutateRate) {
        functionType = function;
        generationNumber = generations;
        mutationRate = mutateRate;
        Population myPop = new Population(populationSize, true);
        showPopulation(myPop);
        System.out.println("---------------------START---------------------");
        int generationCount = 1;
        while (generationCount <= generationNumber) {
            System.out.println("Generation: " + generationCount
                    + " Best found value: " + myPop.getBest().getFunctionValue()
                    + " (x = " + myPop.getBest().getDecimalValue() + ")");
            myPop = evolvePopulation(myPop);
            showPopulation(myPop);
            generationCount++;
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount
                + " Best found value: " + myPop.getBest().getFunctionValue()
                + " (x = " + myPop.getBest().getDecimalValue() + ")");
        return true;
    }

    public void showPopulation(Population population) {
        for (int i = 0; i < population.getChromosomes().size(); i++) {
            System.out.println("Chromosome " + (i + 1) + ": " + population.getChromosome(i).toString()
                    + " (" + population.getChromosome(i).getDecimalValue() + ")");
        }
    }

    public Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.getChromosomes().size(), false);

        for (int i = 0; i < pop.getChromosomes().size(); i++) {
            Chromosome indiv1 = tournamentSelection(pop);
            Chromosome indiv2 = tournamentSelection(pop);
            Chromosome newIndiv = crossover(indiv1, indiv2);
            newPopulation.getChromosomes().add(i, newIndiv);
        }

        for (int i = 0; i < newPopulation.getChromosomes().size(); i++) {
            mutate(newPopulation.getChromosome(i));
        }
        return newPopulation;
    }

    private Chromosome crossover(Chromosome indiv1, Chromosome indiv2) {
        Chromosome newSol = new Chromosome();
        for (int i = 0; i < Chromosome.CHROMOSOME_LENGTH; i++) {
            if (Math.random() <= uniformRate) {
                newSol.setSingleGene(i, indiv1.getSingleGene(i));
            } else {
                newSol.setSingleGene(i, indiv2.getSingleGene(i));
            }
        }
        return newSol;
    }

    private void mutate(Chromosome chromosome) {
        for (int i = 0; i < Chromosome.CHROMOSOME_LENGTH; i++) {
            if (Math.random() <= mutationRate) {
                byte actualGene = chromosome.getSingleGene(i);
                byte newGene = actualGene == 0 ? (byte) 1 : 0;
                chromosome.setSingleGene(i, newGene);
            }
        }
    }

    private Chromosome tournamentSelection(Population pop) {
        Population tournament = new Population(tournamentSize, false);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getChromosomes().size());
            tournament.getChromosomes().add(i, pop.getChromosome(randomId));
        }
        return tournament.getBest();
    }

    protected static double getFunctionValue(Chromosome chromosome) {
        return FunctionUtils.getValueInPoint(functionType, chromosome.getDecimalValue());
    }

}
