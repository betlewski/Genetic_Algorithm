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
                    + " (x = " + myPop.getBest().getDecimalValue() + ")");
            myPop = evolvePopulation(myPop);
            showPopulation(myPop);
            generationCount++;
        }
        System.out.println("--------------------FINISH--------------------");
        System.out.println("Generation: " + generationCount
                + " Best found value: " + myPop.getBest().getFunctionValue()
                + " (x = " + myPop.getBest().getDecimalValue() + ")");
    }

    private void showPopulation(Population population) {
        for (int i = 0; i < populationSize; i++) {
            System.out.println("Chromosome " + (i + 1) + ": " + population.getChromosome(i).toString()
                    + " (" + population.getChromosome(i).getDecimalValue() + ")");
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
        for (Chromosome chromosome : population.getChromosomes()) {
            totalSum += getFunctionValue(chromosome);
        }
        for (int j = 0; j < populationSize; j++) {
            double random = Math.random() * totalSum;
            double partialSum = 0;
            for (Chromosome chromosome : population.getChromosomes()) {
                partialSum += getFunctionValue(chromosome);
                if (partialSum >= random) {
                    newPopulation.getChromosomes().add(chromosome);
                    break;
                }
            }
        }
        return newPopulation;
    }

    private double getFunctionValue(Chromosome chromosome) {
        return FunctionUtils.getValueInPoint(functionType, chromosome.getDecimalValue());
    }

    private Population crossover(Population population) {
        Population newPopulation = new Population(populationSize, false);
        if (population.getChromosomes().size() % 2 == 1) {
            int randomIndex = new Random().nextInt(population.getChromosomes().size());
            Chromosome chromosome = population.getChromosome(randomIndex);
            population.getChromosomes().remove(chromosome);
            newPopulation.getChromosomes().add(chromosome);
        }
        while (!population.getChromosomes().isEmpty()) {
            int randomIndex1 = new Random().nextInt(population.getChromosomes().size());
            Chromosome chromosome1 = population.getChromosome(randomIndex1);
            population.getChromosomes().remove(chromosome1);

            int randomIndex2 = new Random().nextInt(population.getChromosomes().size());
            Chromosome chromosome2 = population.getChromosome(randomIndex2);
            population.getChromosomes().remove(chromosome2);

            pairCrossover(chromosome1, chromosome2);
            newPopulation.getChromosomes().add(chromosome1);
            newPopulation.getChromosomes().add(chromosome2);
        }
        return newPopulation;
    }

    private void pairCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        if (Math.random() <= crossoverRate) {
            int locus = new Random().nextInt(Chromosome.CHROMOSOME_LENGTH - 2) + 1;
            for (int i = locus; i < Chromosome.CHROMOSOME_LENGTH; i++) {
                byte swappedGene = chromosome1.getSingleGene(i);
                chromosome1.setSingleGene(i, chromosome2.getSingleGene(i));
                chromosome2.setSingleGene(i, swappedGene);
            }
        }
    }

    private void mutate(Population population) {
        for (Chromosome chromosome : population.getChromosomes()) {
            for (int i = 0; i < Chromosome.CHROMOSOME_LENGTH; i++) {
                if (Math.random() <= mutationRate) {
                    byte actualGene = chromosome.getSingleGene(i);
                    byte newGene = actualGene == 0 ? (byte) 1 : 0;
                    chromosome.setSingleGene(i, newGene);
                }
            }
        }
    }

}
