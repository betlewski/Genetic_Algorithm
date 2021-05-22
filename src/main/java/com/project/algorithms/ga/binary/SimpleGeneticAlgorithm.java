package com.project.algorithms.ga.binary;

import lombok.Data;

@Data
public class SimpleGeneticAlgorithm {

    private static FunctionType functionType;
    private static int populationSize;
    private static int generationNumber;
    private static double crossoverRate;
    private static double mutationRate;

    public boolean runAlgorithm(FunctionType function, int populationValue, int generations, double crossRate, double mutateRate) {
        functionType = function;
        populationSize = populationValue;
        generationNumber = generations;
        crossoverRate = crossRate;
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
        for (int i = 0; i < populationSize; i++) {
            System.out.println("Chromosome " + (i + 1) + ": " + population.getChromosome(i).toString()
                    + " (" + population.getChromosome(i).getDecimalValue() + ")");
        }
    }

    public Population evolvePopulation(Population population) {
        Population newPopulation = new Population(populationSize, false);
        rouletteWheelSelection(population, newPopulation);
//        crossover();

        for (int i = 0; i < populationSize; i++) {
            mutate(newPopulation.getChromosome(i));
        }
        return newPopulation;
    }

    private void rouletteWheelSelection(Population oldPopulation, Population newPopulation) {
        double totalSum = 0;
        for (int i = 0; i < populationSize; i++) {
            totalSum += getFunctionValue(oldPopulation.getChromosome(i));
        }
        for (int j = 0; j < populationSize; j++) {
            double random = Math.random() * totalSum;
            double partialSum = 0;
            for (int i = 0; i < populationSize; i++) {
                partialSum += getFunctionValue(oldPopulation.getChromosome(i));
                if (partialSum >= random) {
                    Chromosome drawnChromosome = oldPopulation.getChromosome(i);
                    newPopulation.getChromosomes().add(drawnChromosome);
                    break;
                }
            }
        }
    }

    private void crossover(Chromosome chromosome1, Chromosome chromosome2) {
        if (Math.random() <= crossoverRate) {
            int locus = (int) Math.round(Math.random() * (Chromosome.CHROMOSOME_LENGTH - 2)) + 1;
            for (int i = locus; i < Chromosome.CHROMOSOME_LENGTH; i++) {
                byte swappedGene = chromosome1.getSingleGene(i);
                chromosome1.setSingleGene(i, chromosome2.getSingleGene(i));
                chromosome2.setSingleGene(i, swappedGene);
            }
        }
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

    protected static double getFunctionValue(Chromosome chromosome) {
        return FunctionUtils.getValueInPoint(functionType, chromosome.getDecimalValue());
    }

}
