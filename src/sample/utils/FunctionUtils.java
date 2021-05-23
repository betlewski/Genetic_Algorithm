package sample.utils;

/**
 * Function utils for calculating value in point.
 */
public class FunctionUtils {

    private static final int PRECISION = 1000;

    public static double getValueInPoint(FunctionType functionType, double x, double y) {
        switch (functionType) {
            case ACKLEYS:
                return ackleysFunction(x, y);
            case BOOTHS:
                return boothsFunction(x, y);
            case THREE_HUMP_CAMEL:
                return threeHumpCamelFunction(x, y);
            default:
                throw new IllegalArgumentException("Optimization function has not been chosen.");
        }
    }

    /**
     * Perform Ackley's function.
     * Domain is [5, 5]
     * Minimum is 0 at x = 0 & y = 0.
     */
    public static double ackleysFunction(double x, double y) {
        double p1 = -20 * Math.exp(-0.2 * Math.sqrt(0.5 * ((x * x) + (y * y))));
        double p2 = Math.exp(0.5 * (Math.cos(2 * Math.PI * x) + Math.cos(2 * Math.PI * y)));
        return (double) Math.round((p1 - p2 + Math.E + 20) * PRECISION) / PRECISION;
    }

    /**
     * Perform Booth's function.
     * Domain is [-5, 5]
     * Minimum is 0 at x = 1 & y = 3.
     */
    public static double boothsFunction(double x, double y) {
        double p1 = Math.pow(x + 2 * y - 7, 2);
        double p2 = Math.pow(2 * x + y - 5, 2);
        return (double) Math.round((p1 + p2) * PRECISION) / PRECISION;
    }

    /**
     * Perform the Three-Hump Camel function.
     * Domain is [-5, 5]
     * Minimum is 0 at x = 0 & y = 0.
     */
    public static double threeHumpCamelFunction(double x, double y) {
        double p1 = 2 * x * x;
        double p2 = 1.05 * Math.pow(x, 4);
        double p3 = Math.pow(x, 6) / 6;
        return (double) Math.round((p1 - p2 + p3 + x * y + y * y) * PRECISION) / PRECISION;
    }

}
