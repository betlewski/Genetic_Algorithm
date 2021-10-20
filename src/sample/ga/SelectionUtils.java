package sample.ga;

import sample.utils.Logger;

import java.util.Comparator;
import java.util.Random;

public class SelectionUtils {

    public static Population selection(SelectionType selectionType, Population population, Logger logger) {
        switch (selectionType) {
            case ROULETTE_WHEEL:
                return rouletteWheelSelection(population, logger);
            case RANK:
                return rankSelection(population, logger);
            case TOURNAMENT:
                return tournamentSelection(population, logger);
            default:
                throw new IllegalArgumentException("The selection type has not been set.");
        }
    }

    private static Population rouletteWheelSelection(Population population, Logger logger) {
        int populationSize = population.getChromosomePairs().size();
        Population newPopulation = new Population(populationSize, false);
        double totalSum = 0;
        for (ChromosomePair chromosomePair : population.getChromosomePairs()) {
            totalSum += chromosomePair.getFitness();
        }
        logger.startSelectionWithSumAndPopulation(totalSum, population);
        for (int j = 0; j < populationSize; j++) {
            double random = Math.random() * totalSum;
            double partialSum = 0;
            for (int i = 0; i < populationSize; i++) {
                ChromosomePair chromosomePair = population.getChromosomePair(i);
                partialSum += chromosomePair.getFitness();
                if (partialSum >= random) {
                    newPopulation.getChromosomePairs().add(chromosomePair.clone());
                    logger.runSelection(j + 1, random, i + 1);
                    break;
                }
            }
        }
        return newPopulation;
    }

    private static Population rankSelection(Population population, Logger logger) {
        int populationSize = population.getChromosomePairs().size();
        Population newPopulation = new Population(populationSize, false);
        population.getChromosomePairs().sort(Comparator.comparingDouble(ChromosomePair::getFitness));
        double rankSum = (1.0 + populationSize) * populationSize / 2.0; // suma ciągu arytmetycznego (1, n)
        logger.startSelectionWithSumAndSortedPopulation(rankSum, population);
        for (int j = 0; j < populationSize; j++) {
            double random = Math.random() * rankSum;
            double partialRankSum = 0;
            for (int i = 0; i < populationSize; i++) {
                partialRankSum += (i + 1);
                if (partialRankSum >= random) {
                    ChromosomePair chromosomePair = population.getChromosomePair(i);
                    newPopulation.getChromosomePairs().add(chromosomePair.clone());
                    logger.runSelection(j + 1, random, i + 1);
                    break;
                }
            }
        }
        return newPopulation;
    }

    private static Population tournamentSelection(Population population, Logger logger) {
        int populationSize = population.getChromosomePairs().size();
        logger.startSelection();
        Population newPopulation = new Population(populationSize, false);
        for (int j = 0; j < populationSize; j++) {
            int randomIndex1 = new Random().nextInt(population.getChromosomePairs().size());
            int randomIndex2 = new Random().nextInt(population.getChromosomePairs().size());
            while (randomIndex1 == randomIndex2) {
                randomIndex2 = new Random().nextInt(population.getChromosomePairs().size());
            }
            ChromosomePair chromosomePair1 = population.getChromosomePairs().get(randomIndex1);
            ChromosomePair chromosomePair2 = population.getChromosomePairs().get(randomIndex2);
            if (chromosomePair1.getFitness() > chromosomePair2.getFitness()) {
                newPopulation.getChromosomePairs().add(chromosomePair1);
                logger.runTournamentSelection(j + 1, chromosomePair1, chromosomePair2, chromosomePair1);
            } else {
                newPopulation.getChromosomePairs().add(chromosomePair2);
                logger.runTournamentSelection(j + 1, chromosomePair1, chromosomePair2, chromosomePair2);
            }
        }
        return newPopulation;
    }

}
