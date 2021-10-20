package sample.utils;

/**
 * Function utils for calculating function fitness.
 */
public class FunctionUtils {

    public static FunctionType functionType;
    private static final double MAX = 100000000;
    private static final int PRECISION = 1000;

    public static double getFunctionFitness(double x, double y) {
        return MAX - getValueInPoint(x, y);
    }

    public static double getValueInPoint(double x, double y) {
        switch (functionType) {
            case ACKLEY:
                return ackleyFunction(x, y);
            case BOOTH:
                return boothFunction(x, y);
            case EGGHOLDER:
                return eggholderFunction(x, y);
            case GOLDSTEIN_PRICE:
                return goldsteinPriceFunction(x, y);
            case MCCORMICK:
                return mcCormickFunction(x, y);
            case RASTRIGIN:
                return rastriginFunction(x, y);
            case THREE_HUMP_CAMEL:
                return threeHumpCamelFunction(x, y);
            default:
                throw new IllegalArgumentException("Optimization function has not been chosen.");
        }
    }

    /**
     * Perform Ackley function.
     * Domain is [-4, 4]
     * Minimum is 0 at x = 0 & y = 0.
     */
    private static double ackleyFunction(double x, double y) {
        double p1 = -20 * Math.exp(-0.2 * Math.sqrt(0.5 * ((x * x) + (y * y))));
        double p2 = Math.exp(0.5 * (Math.cos(2 * Math.PI * x) + Math.cos(2 * Math.PI * y)));
        return (double) Math.round((p1 - p2 + Math.E + 20) * PRECISION) / PRECISION;
    }

    /**
     * Perform Booth function.
     * Domain is [-4, 4]
     * Minimum is 0 at x = 1 & y = 3.
     */
    private static double boothFunction(double x, double y) {
        double p1 = Math.pow(x + 2 * y - 7, 2);
        double p2 = Math.pow(2 * x + y - 5, 2);
        return (double) Math.round((p1 + p2) * PRECISION) / PRECISION;
    }

    /**
     * Perform Eggholder function.
     * Domain is [-4, 4]
     * Minimum is -45 at x = 4 & y = 4.
     */
    private static double eggholderFunction(double x, double y) {
        double p1 = -(y + 47) * Math.sin(Math.sqrt(Math.abs(y + x / 2 + 47)));
        double p2 = -x * Math.sin(Math.sqrt(Math.abs(x - (y + 47))));
        return (double) Math.round((p1 + p2) * PRECISION) / PRECISION;
    }

    /**
     * Perform Goldstein-Price function.
     * Domain is [-4, 4]
     * Minimum is 3 at x = 0 & y = -1.
     */
    private static double goldsteinPriceFunction(double x, double y) {
        double p1a = Math.pow((x + y + 1), 2);
        double p1b = 19 - 14 * x + 3 * Math.pow(x, 2) - 14 * y + 6 * x * y + 3 * Math.pow(y, 2);
        double p1 = 1 + p1a * p1b;
        double p2a = Math.pow((2 * x - 3 * y), 2);
        double p2b = 18 - 32 * x + 12 * Math.pow(x, 2) + 48 * y - 36 * x * y + 27 * Math.pow(y, 2);
        double p2 = 30 + p2a * p2b;
        return (double) Math.round((p1 * p2) * PRECISION) / PRECISION;
    }

    /**
     * Perform McCormick function.
     * Domain is [-4, 4]
     * Minimum is -4.44 at x = -3.45 & y = -4.
     */
    private static double mcCormickFunction(double x, double y) {
        double p1 = Math.sin(x + y) + Math.pow(x - y, 2);
        double p2 = -1.5 * x + 2.5 * y + 1;
        return (double) Math.round((p1 + p2) * PRECISION) / PRECISION;
    }

    /**
     * Perform Rastrigin function.
     * Domain is [-4, 4]
     * Minimum is 0 at x = 0 & y = 0.
     */
    private static double rastriginFunction(double x, double y) {
        double p1 = Math.pow(x, 2) - 10 * Math.cos(2 * Math.PI * x);
        double p2 = Math.pow(y, 2) - 10 * Math.cos(2 * Math.PI * y);
        return (double) Math.round((20 + p1 + p2) * PRECISION) / PRECISION;
    }

    /**
     * Perform the Three-Hump Camel function.
     * Domain is [-4, 4]
     * Minimum is 0 at x = 0 & y = 0.
     */
    private static double threeHumpCamelFunction(double x, double y) {
        double p1 = 2 * x * x;
        double p2 = 1.05 * Math.pow(x, 4);
        double p3 = Math.pow(x, 6) / 6;
        return (double) Math.round((p1 - p2 + p3 + x * y + y * y) * PRECISION) / PRECISION;
    }

}
