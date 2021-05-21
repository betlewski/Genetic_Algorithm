package com.project.algorithms.ga.binary;

public class FunctionUtils {

    public static double getValueInPoint(FunctionType functionType, double x) {
        switch (functionType) {
            case ABSOLUTE_X_MINUS_2:
                return Math.abs(x - 2);
            case MINUS_X_TO_THE_POWER_OF_2:
                return Math.pow(x, 2) * (-1);
            case EXPONENTIAL_FOR_2:
                return Math.pow(2, x);
            default:
                return 0;
        }
    }

}
