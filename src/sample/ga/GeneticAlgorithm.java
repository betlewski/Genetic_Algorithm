package sample.ga;

import sample.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sample.ga.SelectionUtils.selection;

public class GeneticAlgorithm {

    private Logger logger;
    private SelectionType selectionType;
    private int populationSize;
    private int generationNumber;
    private double crossoverRate;
    private double mutationRate;

    public static final double DEFAULT_CROSSOVER_RATE = 0.5;
    public static final double DEFAULT_MUTATION_RATE = 0.05;

    public GeneticAlgorithm(FunctionType function, SelectionType selection, int populationValue,
                            int generationValue, double crossRateValue, double mutationRateValue) {
        selectionType = selection;
        populationSize = populationValue;
        generationNumber = generationValue;
        crossoverRate = crossRateValue;
        mutationRate = mutationRateValue;
        FunctionUtils.functionType = function;
    }

    public GeneticAlgorithm(FunctionType function, SelectionType selection, int populationValue, int generationValue) {
        this(function, selection, populationValue, generationValue, DEFAULT_CROSSOVER_RATE, DEFAULT_MUTATION_RATE);
    }

    public Logger getLogger() {
        return logger;
    }

    public String runAlgorithm() {
        logger = new Logger();
        logger.initialize(FunctionUtils.functionType, selectionType, populationSize, generationNumber, crossoverRate, mutationRate);
        Population myPopulation = new Population(populationSize, true);
        logger.startAlgorithm(myPopulation);
        int generationCount = 1;
        while (generationCount <= generationNumber) {
            logger.startGeneration(generationCount);
            myPopulation = evolvePopulation(myPopulation);
            logger.finishGeneration(myPopulation);
            generationCount++;
        }
        logger.writeResults(myPopulation);
        return logger.getResults(myPopulation);
    }

    public void doResearch() {
        final int samples = 1000;
        List<Double> bestValues = new ArrayList<>();
        logger = new Logger();

        for (int k = 1; k <= samples; k++) {
            Population myPopulation = new Population(populationSize, true);
            int generationCount = 1;
            double sampleBest = FunctionUtils.MAX;
            while (generationCount <= generationNumber) {
                myPopulation = evolvePopulation(myPopulation);
                double currentBest = myPopulation.getBest().getFunctionValue();
                if (currentBest < sampleBest) {
                    sampleBest = currentBest;
                }
                generationCount++;
            }
            bestValues.add(sampleBest);
//            System.out.println("---------------------------SAMPLE " + k + "--------------------------");
//            System.out.println("Best Evaluation: " + sampleBest);
        }
        double average = bestValues.stream().mapToDouble(value -> value).sum() / (double) samples;
        double standDeviation = 0.0;
        for (double value : bestValues) {
            standDeviation += Math.pow((value - average), 2);
        }
        standDeviation = Math.sqrt(standDeviation / (double) samples);
        System.out.println("---------------------------COMPLETED-------------------------");
        System.out.println("Selection Type: " + selectionType + " | Population size: " + populationSize);
        System.out.println("Average: " + average);
        System.out.println("Standard Deviation: " + standDeviation);
    }

    private Population evolvePopulation(Population population) {
        Population population1 = selection(selectionType, population, logger);
        Population population2 = crossover(population1);
        mutate(population2);
        return population2;
    }

    private Population crossover(Population population) {
        logger.initCrossing(population);
        Population newPopulation = new Population(populationSize, false);
        if (population.getChromosomePairs().size() % 2 == 1) {
            int randomIndex = new Random().nextInt(population.getChromosomePairs().size());
            ChromosomePair chromosomePair = population.getChromosomePair(randomIndex);
            population.getChromosomePairs().remove(chromosomePair);
            newPopulation.getChromosomePairs().add(chromosomePair);
        }
        while (!population.getChromosomePairs().isEmpty()) {
            int randomIndex1 = new Random().nextInt(population.getChromosomePairs().size());
            ChromosomePair chromosomePair1 = population.getChromosomePairs().remove(randomIndex1);

            int randomIndex2 = new Random().nextInt(population.getChromosomePairs().size());
            ChromosomePair chromosomePair2 = population.getChromosomePairs().remove(randomIndex2);

            pairCrossover(chromosomePair1, chromosomePair2);
            newPopulation.getChromosomePairs().add(chromosomePair1);
            newPopulation.getChromosomePairs().add(chromosomePair2);
        }
        return newPopulation;
    }

    private void pairCrossover(ChromosomePair chromosomePair1, ChromosomePair chromosomePair2) {
        double crossRandom = Math.random();
        logger.startCrossing(chromosomePair1, chromosomePair2, crossRandom, crossRandom <= crossoverRate);
        if (crossRandom <= crossoverRate) {
            int locus = new Random().nextInt(Chromosome.CHROMOSOME_LENGTH - 2) + 1;
            for (int i = locus; i < Chromosome.CHROMOSOME_LENGTH; i++) {
                byte swappedGene = chromosomePair1.getChromosomeX().getSingleGene(i);
                chromosomePair1.getChromosomeX().setSingleGene(i, chromosomePair2.getChromosomeX().getSingleGene(i));
                chromosomePair2.getChromosomeX().setSingleGene(i, swappedGene);

                swappedGene = chromosomePair1.getChromosomeY().getSingleGene(i);
                chromosomePair1.getChromosomeY().setSingleGene(i, chromosomePair2.getChromosomeY().getSingleGene(i));
                chromosomePair2.getChromosomeY().setSingleGene(i, swappedGene);
            }
            logger.runCrossing(chromosomePair1, chromosomePair2, locus);
        }
    }

    private void mutate(Population population) {
        logger.initMutation(population);
        for (ChromosomePair chromosomePair : population.getChromosomePairs()) {
            logger.startMutation(chromosomePair);
            for (int i = 0; i < Chromosome.CHROMOSOME_LENGTH; i++) {
                double mutateRandom = Math.random();
                logger.runMutation(i + 1, mutateRandom, mutateRandom <= mutationRate);
                if (mutateRandom <= mutationRate) {
                    byte actualGene = chromosomePair.getChromosomeX().getSingleGene(i);
                    byte newGene = actualGene == 0 ? (byte) 1 : 0;
                    chromosomePair.getChromosomeX().setSingleGene(i, newGene);

                    actualGene = chromosomePair.getChromosomeY().getSingleGene(i);
                    newGene = actualGene == 0 ? (byte) 1 : 0;
                    chromosomePair.getChromosomeY().setSingleGene(i, newGene);
                }
            }
            logger.finishMutation(chromosomePair);
        }
    }

}
