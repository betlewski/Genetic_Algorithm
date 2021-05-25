package sample.utils;

import sample.ga.ChromosomePair;
import sample.ga.Population;
import sample.ga.SelectionType;

public class Logger {

    private FileHandler fileHandler;

    public Logger() {
        fileHandler = new FileHandler();
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void initialize(FunctionType function, SelectionType selectionType, int populationValue,
                           int generationValue, double crossRateValue, double mutationRateValue) {
        fileHandler.log(function.description);
        String initialParam = "\n--------------------INITIAL PARAMETERS--------------------\n" +
                "Population size: " + populationValue + "\n" +
                "Generation number: " + generationValue + "\n" +
                "Selection type: " + selectionType + "\n" +
                "Crossover rate: " + crossRateValue + "\n" +
                "Mutation rate: " + mutationRateValue + "\n" +
                "----------------------------------------------------------";
        fileHandler.log(initialParam);
    }

    private void writeChromosome(ChromosomePair chromosomePair) {
        String chromosomeText = "Chromosome: (value = " + chromosomePair.getFunctionValue() + ")\n"
                + " X = " + chromosomePair.getChromosomeX() + "\n"
                + " Y = " + chromosomePair.getChromosomeY();
        fileHandler.log(chromosomeText);
    }

    private void saveGeneration(Population population) {
        int counter = 1;
        for (ChromosomePair chromosomePair : population.getChromosomePairs()) {
            String generationText = "Chromosome " + counter + ": (value = " + chromosomePair.getFunctionValue() + ")\n"
                    + " X = " + chromosomePair.getChromosomeX() + "\n"
                    + " Y = " + chromosomePair.getChromosomeY();
            fileHandler.log(generationText);
            counter++;
        }
    }

    public void startAlgorithm(Population population) {
        startGeneration(0);
        saveGeneration(population);
    }

    public void startGeneration(int generationCount) {
        fileHandler.log("\n\n#################### GENERATION " + generationCount + " ####################\n");
    }

    public void startSelection() {
        fileHandler.log("--------------------SELECTION--------------------\n");
    }

    public void startSelectionWithSum(double totalSum) {
        startSelection();
        fileHandler.log("Total sum: " + totalSum + "\n");
    }

    public void startSelectionWithSumAndSortedPopulation(double totalSum, Population population) {
        startSelectionWithSum(totalSum);
        fileHandler.log("~~~~~~~~~~ Sorted population ~~~~~~~~~~\n");
        saveGeneration(population);
        fileHandler.newLine();
    }

    public void runSelection(int order, double randomNumber, int foundIndex) {
        fileHandler.log("Random " + order + ": " + randomNumber + " -> Chromosome " + foundIndex);
    }

    public void runTournamentSelection(int order, ChromosomePair chromosomePair1,
                                       ChromosomePair chromosomePair2, ChromosomePair winnerChromosome) {
        fileHandler.log("\n********** Round " + order + " **********\n");
        writeChromosome(chromosomePair1);
        writeChromosome(chromosomePair2);
        fileHandler.log("\nWinner:\n");
        writeChromosome(winnerChromosome);
    }

    public void initCrossing(Population population) {
        fileHandler.log("\n\n~~~~~~~~~~ Population after selection ~~~~~~~~~~\n");
        saveGeneration(population);
        fileHandler.log("\n--------------------CROSSOVER--------------------");
    }

    public void startCrossing(ChromosomePair chromosomePair1, ChromosomePair chromosomePair2,
                              double crossRandom, boolean condition) {
        fileHandler.log("\n********** Crossing pair **********\n");
        writeChromosome(chromosomePair1);
        writeChromosome(chromosomePair2);
        if (condition) {
            fileHandler.log("\nRandom crossover rate: " + crossRandom + " => CROSS");
        } else {
            fileHandler.log("\nRandom crossover rate: " + crossRandom + " => NOT CROSS");
        }
    }

    public void runCrossing(ChromosomePair chromosomePair1, ChromosomePair chromosomePair2, int locus) {
        fileHandler.log("Locus: " + locus + "\n");
        fileHandler.log("Pair after crossing:\n");
        writeChromosome(chromosomePair1);
        writeChromosome(chromosomePair2);
    }

    public void initMutation(Population population) {
        fileHandler.log("\n\n~~~~~~~~~~ Population after crossing ~~~~~~~~~~\n");
        saveGeneration(population);
        fileHandler.log("\n--------------------MUTATION--------------------");
    }

    public void startMutation(ChromosomePair chromosomePair) {
        fileHandler.log("\n********** Chromosome mutation **********\n");
        writeChromosome(chromosomePair);
    }

    public void runMutation(int index, double mutateRandom, boolean condition) {
        fileHandler.log("\nGene index: " + index);
        if (condition) {
            fileHandler.log("Random mutation rate: " + mutateRandom + " => MUTATE");
        } else {
            fileHandler.log("Random mutation rate: " + mutateRandom + " => NOT MUTATE");
        }
    }

    public void finishMutation(ChromosomePair chromosomePair) {
        fileHandler.log("\nChromosome after mutation:\n");
        writeChromosome(chromosomePair);
    }

    public void finishGeneration(Population population) {
        fileHandler.log("\n~~~~~~~~~~ Population after mutation ~~~~~~~~~~\n");
        saveGeneration(population);
    }

    public void writeResults(Population population) {
        fileHandler.log("\n\n#################### RESULTS ####################\n");
        String results = getResults(population);
        fileHandler.log(results);
    }

    public String getResults(Population population) {
        return "Best found value: " + population.getBest().getFunctionValue() + "\n"
                + " X = " + population.getBest().getChromosomeX()
                + " (" + population.getBest().getChromosomeX().getDecimalValue() + ")\n"
                + " Y = " + population.getBest().getChromosomeY()
                + " (" + population.getBest().getChromosomeY().getDecimalValue() + ")";
    }

}
