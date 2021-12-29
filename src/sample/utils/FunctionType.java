package sample.utils;

/**
 * Function types to chosen for optimization.
 */
public enum FunctionType {

    ACKLEY("Ackley", FunctionType.ACKLEY_DESC),
    BOOTH("Booth", FunctionType.BOOTH_DESC),
    EGGHOLDER("Eggholder", FunctionType.EGGHOLDER_DESC),
    GOLDSTEIN_PRICE("Goldstein-Price", FunctionType.GOLDSTEIN_PRICE_DESC),
    LEVY("Levy N. 13", FunctionType.LEVY_DESC),
    MCCORMICK("McCormick", FunctionType.MCCORMICK_DESC),
    RASTRIGIN("Rastrigin", FunctionType.RASTRIGIN_DESC),
    THREE_HUMP_CAMEL("Three-hump camel", FunctionType.THREE_HUMP_CAMEL_DESC);

    public final String name;
    public final String description;

    FunctionType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public static FunctionType[] types() {
        return new FunctionType[]{
                GOLDSTEIN_PRICE, LEVY, MCCORMICK, RASTRIGIN
        };
    }

    private static final String ACKLEY_DESC = "Optimization for Ackley function...\n" +
            "Domain is [-4, 4]. Minimum value is 0 at point (0,0).";

    private static final String BOOTH_DESC = "Optimization for Booth function...\n" +
            "Domain is [-4, 4]. Minimum value is 0 at point (1,3).";

    private static final String EGGHOLDER_DESC = "Optimization for Eggholder function...\n" +
            "Domain is [-4, 4]. Minimum value is -45 at point (4,4).";

    private static final String GOLDSTEIN_PRICE_DESC = "Optimization for Goldstein-Price function...\n" +
            "Domain is [-4, 4]. Minimum value is 3 at point (0,-1).";

    private static final String LEVY_DESC = "Optimization for Levy function N.13...\n" +
            "Domain is [-4, 4]. Minimum value is 0 at point (1,1).";

    private static final String MCCORMICK_DESC = "Optimization for McCormick function...\n" +
            "Domain is [-4, 4]. Minimum value is -4.44 at point (-3.45,-4).";

    private static final String RASTRIGIN_DESC = "Optimization for Rastrigin function...\n" +
            "Domain is [-4, 4]. Minimum value is 0 at point (0,0).";

    private static final String THREE_HUMP_CAMEL_DESC = "Optimization for the Three-Hump Camel function...\n" +
            "Domain is [-4, 4]. Minimum value is 0 at point (0,0).";

}