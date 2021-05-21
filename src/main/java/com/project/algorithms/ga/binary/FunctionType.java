package com.project.algorithms.ga.binary;

public enum FunctionType {

    ABSOLUTE_X_MINUS_2(0, 15, 13),
    MINUS_X_TO_THE_POWER_OF_2(0, 15, 0),
    EXPONENTIAL_FOR_2(0, 15, 32768);

    private final Integer startPoint;
    private final Integer endPoint;
    private final Integer maxValue;

    FunctionType(Integer startPoint, Integer endPoint, Integer maxValue) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.maxValue = maxValue;
    }

    public Integer getStartPoint() {
        return startPoint;
    }

    public Integer getEndPoint() {
        return endPoint;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

}
