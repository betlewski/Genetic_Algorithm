package sample.ga;

import lombok.Data;
import sample.utils.FunctionType;
import sample.utils.FunctionUtils;
import sample.utils.Logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

@Data
public class GeneticAlgorithm {

    private Logger logger;
    private static FunctionType functionType;
    private static int populationSize;
    private static int generationNumber;
    private static double crossoverRate;
    private static double mutationRate;

    public static final double DEFAULT_CROSSOVER_RATE = 0.5;
    public static final double DEFAULT_MUTATION_RATE = 0.05;

    public GeneticAlgorithm(FunctionType function, int populationValue, int generationValue,
                            double crossRateValue, double mutationRateValue) {
        functionType = function;
        populationSize = populationValue;
        generationNumber = generationValue;
        crossoverRate = crossRateValue;
        mutationRate = mutationRateValue;
    }

    public GeneticAlgorithm(FunctionType function, int populationValue, int generationValue) {
        this(function, populationValue, generationValue, DEFAULT_CROSSOVER_RATE, DEFAULT_MUTATION_RATE);
    }

    public Logger getLogger() {
        return logger;
    }

    public String runAlgorithm() {
        logger = new Logger();
        logger.initialize(functionType, populationSize, generationNumber, crossoverRate, mutationRate);
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

    private Population evolvePopulation(Population population) {
        Population population1 = rankSelection(population);
        Population population2 = crossover(population1);
        mutate(population2);
        return population2;
    }

    private Population rouletteWheelSelection(Population population) {
        Population newPopulation = new Population(populationSize, false);
        double totalReversedSum = 0;
        for (ChromosomePair chromosomePair : population.getChromosomePairs()) {
            totalReversedSum += (1 / getFunctionValue(chromosomePair));
        }
        logger.startSelection(totalReversedSum);
        for (int j = 0; j < populationSize; j++) {
            double random = Math.random() * totalReversedSum;
            double partialReversedSum = 0;
            for (int i = 0; i < populationSize; i++) {
                ChromosomePair chromosomePair = population.getChromosomePair(i);
                partialReversedSum += (1 / getFunctionValue(chromosomePair));
                if (partialReversedSum >= random) {
                    newPopulation.getChromosomePairs().add(chromosomePair.clone());
                    logger.runSelection(j + 1, random, i + 1);
                    break;
                }
            }
        }
        return newPopulation;
    }

    private Population rankSelection(Population population) {
        Population newPopulation = new Population(populationSize, false);
        population.getChromosomePairs().sort(Comparator.comparingDouble(ChromosomePair::getFunctionValue));
        Collections.reverse(population.getChromosomePairs());
        double rankSum = 0;
        for (int i = 0; i < populationSize; i++) {
            rankSum += (double) (i + 1) / populationSize;
        }
        logger.startSelection(rankSum);
        for (int j = 0; j < populationSize; j++) {
            double random = Math.random() * rankSum;
            double partialRankSum = 0;
            for (int i = 0; i < populationSize; i++) {
                ChromosomePair chromosomePair = population.getChromosomePair(i);
                partialRankSum += (double) (i + 1) / populationSize;
                if (partialRankSum >= random) {
                    newPopulation.getChromosomePairs().add(chromosomePair.clone());
                    logger.runSelection(j + 1, random, i + 1);
                    break;
                }
            }
        }
        return newPopulation;
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

    protected static double getFunctionValue(ChromosomePair chromosomePair) {
        double valueX = chromosomePair.getChromosomeX().getDecimalValue();
        double valueY = chromosomePair.getChromosomeY().getDecimalValue();
        return FunctionUtils.getValueInPoint(functionType, valueX, valueY);
    }

}
