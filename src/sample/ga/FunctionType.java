package sample.ga;

/**
 * Function types to chosen for optimization.
 */
public enum FunctionType {

    ACKLEYS("Ackley's", FunctionType.ACKLEYS_DESC),
    BOOTHS("Booth's", FunctionType.BOOTHS_DESC),
    THREE_HUMP_CAMEL("Three-hump camel", FunctionType.THREE_HUMP_CAMEL_DESC);

    private final String name;
    private final String description;

    FunctionType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getName();
    }

    private static final String ACKLEYS_DESC = "Optimization for Ackley's function...\n" +
            "Domain is [5, 5]. Minimum value is 0 at point (0,0).";

    private static final String BOOTHS_DESC = "Optimization for Booth's function...\n" +
            "Domain is [5, 5]. Minimum value is 0 at point (1,3).";

    private static final String THREE_HUMP_CAMEL_DESC = "Optimization for the Three-Hump Camel function...\n" +
            "Domain is [5, 5]. Minimum value is 0 at point (0,0).";

}