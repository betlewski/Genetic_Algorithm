package sample.utils;

import sample.ga.ChromosomePair;
import sample.ga.Population;

public class Logger {

    private FileHandler fileHandler;

    public Logger() {
        fileHandler = new FileHandler();
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void initialize(FunctionType function, int populationValue, int generationValue,
                           double crossRateValue, double mutationRateValue) {
        fileHandler.log(function.getDescription());
        String initialParam = "\n--------------------INITIAL PARAMETERS--------------------\n" +
                "Population size: " + populationValue + "\n" +
                "Generation number: " + generationValue + "\n" +
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

    private void saveGenerationWithReversed(Population population) {
        int counter = 1;
        for (ChromosomePair chromosomePair : population.getChromosomePairs()) {
            double reversedValue = Math.round((1.0 / chromosomePair.getFunctionValue()) * 1000.0) / 1000.0;
            String generationText = "Chromosome " + counter + ": (value = " + chromosomePair.getFunctionValue()
                    + ", reversed = " + reversedValue + ")\n"
                    + " X = " + chromosomePair.getChromosomeX() + "\n"
                    + " Y = " + chromosomePair.getChromosomeY();
            fileHandler.log(generationText);
            counter++;
        }
    }

    public void startAlgorithm(Population population) {
        startGeneration(0);
        saveGenerationWithReversed(population);
    }

    public void startGeneration(int generationCount) {
        fileHandler.log("\n\n#################### GENERATION " + generationCount + " ####################\n");
    }

    public void startSelection(double totalReversedSum) {
        fileHandler.log("--------------------SELECTION--------------------\n");
        fileHandler.log("Total reversed sum: " + totalReversedSum + "\n");
    }

    public void runSelection(int order, double randomNumber, int foundIndex) {
        fileHandler.log("Random " + order + ": " + randomNumber + " -> Chromosome " + foundIndex);
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
        saveGenerationWithReversed(population);
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
