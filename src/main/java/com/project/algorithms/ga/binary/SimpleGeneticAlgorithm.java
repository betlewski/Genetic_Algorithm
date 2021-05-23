package com.project.algorithms.ga.binary;

import lombok.Data;

import java.util.Random;

@Data
public class SimpleGeneticAlgorithm {

    private static FunctionType functionType;
    private static int populationSize;
    private static int generationNumber;
    private static double crossoverRate;
    private static double mutationRate;

    public void runAlgorithm(FunctionType function, int populationValue, int generations, double crossRate, double mutateRate) {
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
                    + " (x = " + myPop.getBest().getChromosomeX().getDecimalValue()
                    + " | y = " + myPop.getBest().getChromosomeY().getDecimalValue() + ")");
            myPop = evolvePopulation(myPop);
            showPopulation(myPop);
            generationCount++;
        }
        System.out.println("--------------------FINISH--------------------");
        System.out.println("Generation: " + generationCount
                + " Best found value: " + myPop.getBest().getFunctionValue()
                + " (x = " + myPop.getBest().getChromosomeX().getDecimalValue()
                + " | y = " + myPop.getBest().getChromosomeY().getDecimalValue() + ")");
    }

    private void showPopulation(Population population) {
        for (int i = 0; i < populationSize; i++) {
            System.out.println("Chromosome " + (i + 1) + ": "
                    + " x = " + population.getChromosomePair(i).getChromosomeX().toString()
                    + " (" + population.getChromosomePair(i).getChromosomeX().getDecimalValue() + ")"
                    + " | y = " + population.getChromosomePair(i).getChromosomeY().toString()
                    + " (" + population.getChromosomePair(i).getChromosomeY().getDecimalValue() + ")");
        }
    }

    private Population evolvePopulation(Population population) {
        Population population1 = rouletteWheelSelection(population);
        Population population2 = crossover(population1);
        mutate(population2);
        return population2;
    }

    private Population rouletteWheelSelection(Population population) {
        Population newPopulation = new Population(populationSize, false);
        double totalSum = 0;
        for (ChromosomePair chromosomePair : population.getChromosomePairs()) {
            totalSum += getFunctionValue(chromosomePair);
        }
        for (int j = 0; j < populationSize; j++) {
            double random = Math.random() * totalSum;
            double partialSum = 0;
            for (ChromosomePair chromosomePair : population.getChromosomePairs()) {
                partialSum += getFunctionValue(chromosomePair);
                if (partialSum >= random) {
                    newPopulation.getChromosomePairs().add(chromosomePair);
                    break;
                }
            }
        }
        return newPopulation;
    }

    private Population crossover(Population population) {
        Population newPopulation = new Population(populationSize, false);
        if (population.getChromosomePairs().size() % 2 == 1) {
            int randomIndex = new Random().nextInt(population.getChromosomePairs().size());
            ChromosomePair chromosomePair = population.getChromosomePair(randomIndex);
            population.getChromosomePairs().remove(chromosomePair);
            newPopulation.getChromosomePairs().add(chromosomePair);
        }
        while (!population.getChromosomePairs().isEmpty()) {
            int randomIndex1 = new Random().nextInt(population.getChromosomePairs().size());
            ChromosomePair chromosomePair1 = population.getChromosomePair(randomIndex1);
            population.getChromosomePairs().remove(chromosomePair1);

            int randomIndex2 = new Random().nextInt(population.getChromosomePairs().size());
            ChromosomePair chromosomePair2 = population.getChromosomePair(randomIndex2);
            population.getChromosomePairs().remove(chromosomePair2);

            pairCrossover(chromosomePair1, chromosomePair2);
            newPopulation.getChromosomePairs().add(chromosomePair1);
            newPopulation.getChromosomePairs().add(chromosomePair2);
        }
        return newPopulation;
    }

    private void pairCrossover(ChromosomePair chromosomePair1, ChromosomePair chromosomePair2) {
        if (Math.random() <= crossoverRate) {
            int locus = new Random().nextInt(Chromosome.CHROMOSOME_LENGTH - 2) + 1;
            for (int i = locus; i < Chromosome.CHROMOSOME_LENGTH; i++) {
                byte swappedGene = chromosomePair1.getChromosomeX().getSingleGene(i);
                chromosomePair1.getChromosomeX().setSingleGene(i, chromosomePair2.getChromosomeX().getSingleGene(i));
                chromosomePair2.getChromosomeX().setSingleGene(i, swappedGene);

                swappedGene = chromosomePair1.getChromosomeY().getSingleGene(i);
                chromosomePair1.getChromosomeY().setSingleGene(i, chromosomePair2.getChromosomeY().getSingleGene(i));
                chromosomePair2.getChromosomeY().setSingleGene(i, swappedGene);
            }
        }
    }

    private void mutate(Population population) {
        for (ChromosomePair chromosomePair : population.getChromosomePairs()) {
            for (int i = 0; i < Chromosome.CHROMOSOME_LENGTH; i++) {
                if (Math.random() <= mutationRate) {
                    byte actualGene = chromosomePair.getChromosomeX().getSingleGene(i);
                    byte newGene = actualGene == 0 ? (byte) 1 : 0;
                    chromosomePair.getChromosomeX().setSingleGene(i, newGene);

                    actualGene = chromosomePair.getChromosomeY().getSingleGene(i);
                    newGene = actualGene == 0 ? (byte) 1 : 0;
                    chromosomePair.getChromosomeY().setSingleGene(i, newGene);
                }
            }
        }
    }

    protected static double getFunctionValue(ChromosomePair chromosomePair) {
        double valueX = chromosomePair.getChromosomeX().getDecimalValue();
        double valueY = chromosomePair.getChromosomeY().getDecimalValue();
        return FunctionUtils.getValueInPoint(functionType, valueX, valueY);
    }

}
