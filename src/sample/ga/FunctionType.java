package sample.ga;

/**
 * Function types to chosen for optimization.
 */
public enum FunctionType {

    ACKLEYS("Ackley's", 15),
    BOOTHS("Booth's", 900),
    THREE_HUMP_CAMEL("Three-hump camel", 2200);

    private final String name;
    private final double maxYRange;

    FunctionType(String name, double maxYRange) {
        this.name = name;
        this.maxYRange = maxYRange;
    }

    public String getName() {
        return name;
    }

    public double getMaxYRange() {
        return maxYRange;
    }

    @Override
    public String toString() {
        return this.name;
    }

}