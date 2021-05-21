package com.project.algorithms.ga.binary;

import lombok.Data;

@Data
public class SimpleGeneticAlgorithm {

    private static FunctionType functionType;
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.025;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    private static final int maxGeneration = 10000;
    private static byte[] solution;

    public boolean runAlgorithm(int populationSize, FunctionType function) {
        functionType = function;
        int range = functionType.getEndPoint() - functionType.getStartPoint();
        solution = new byte[range];
        Population myPop = new Population(populationSize, true);
        showPopulation(myPop);
        System.out.println("---------------------START---------------------");
        int generationCount = 1;
        while (myPop.getBest().getFunctionValue() < functionType.getMaxValue()) {
            System.out.println("Generation: " + generationCount
                    + " Best found value: " + myPop.getBest().getFunctionValue()
                    + " (x = " + myPop.getBest().getDecimalValue() + ")");
            myPop = evolvePopulation(myPop);
            showPopulation(myPop);
            generationCount++;
            if (generationCount > maxGeneration) {
                System.out.println("Solution was not found! " +
                        "The maximum number of generations has been reached...");
                return false;
            }
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
        int elitismOffset;
        Population newPopulation = new Population(pop.getChromosomes().size(), false);

        if (elitism) {
            newPopulation.getChromosomes().add(0, pop.getBest());
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }

        for (int i = elitismOffset; i < pop.getChromosomes().size(); i++) {
            Chromosome indiv1 = tournamentSelection(pop);
            Chromosome indiv2 = tournamentSelection(pop);
            Chromosome newIndiv = crossover(indiv1, indiv2);
            newPopulation.getChromosomes().add(i, newIndiv);
        }

        for (int i = elitismOffset; i < newPopulation.getChromosomes().size(); i++) {
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

    private void mutate(Chromosome indiv) {
        for (int i = 0; i < Chromosome.CHROMOSOME_LENGTH; i++) {
            if (Math.random() <= mutationRate) {
                byte gene = (byte) Math.round(Math.random());
                indiv.setSingleGene(i, gene);
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
