package com.project.algorithms.ga.binary;

import lombok.Data;

@Data
public class Chromosome {

    public static final int CHROMOSOME_LENGTH = 10;
    private byte[] genes = new byte[CHROMOSOME_LENGTH];

    private double functionValue = 0;
    private boolean actualFunctionValue = false;

    public Chromosome() {
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    protected byte getSingleGene(int index) {
        return genes[index];
    }

    protected void setSingleGene(int index, byte value) {
        genes[index] = value;
        actualFunctionValue = false;
    }

    public double getFunctionValue() {
        if (!actualFunctionValue) {
            functionValue = SimpleGeneticAlgorithm.getFunctionValue(this);
            actualFunctionValue = true;
        }
        return functionValue;
    }

    /**
     * The first bit is the sign: 1 for PLUS / 0 for MINUS.
     * The next 2 bites represent the integer part of the number in binary.
     * Last 7 bites are for the fractional part of the number.
     * <p>
     * For example string like this 1101011000 means:
     * (+)(1*2)+(0*1)+(1/2)+(0/4)+(1/8)+(1/16)+(0/32)+(0/64)+(0/128) = 2+(11/16)
     * <p>
     * For ten gens there is -4 as the begin of the range (with all zeros)
     * and 4 as the end of the range (with all ones).
     *
     * @return decimal value of genes
     */
    public double getDecimalValue() {
        double value = 0;
        int factor = 1;
        for (int i = 1; i < CHROMOSOME_LENGTH; i++) {
            double unit = Math.pow(2, factor) * getSingleGene(i);
            value += unit;
            factor--;
        }
        if (getSingleGene(0) == 0) {
            return value * (-1);
        } else {
            return value;
        }
    }

    @Override
    public String toString() {
        StringBuilder geneString = new StringBuilder();
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            geneString.append(getSingleGene(i));
        }
        return geneString.toString();
    }

}
