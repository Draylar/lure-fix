package draylar.lurefix.logic;

public class FishingBobberLogic {

    /**
     * Computes the time reduction for the given level of lure on a fishing rod.
     *
     * <p>Vanilla values for the first 3 levels are kept very similar:
     * <ul>
     *   <li>0 -> 0
     *   <li>1 -> ~100
     *   <li>2 -> ~200
     *   <li>3 -> ~400
     *
     * <p>Lure levels cap at a reduction of 599 (0 to 1 second tick wait), and hit the max values starting at around Lure 10.
     *
     * @param lure  level of lure on the fishing tool
     * @return      the reduction, in ticks, of the initial wait time for this lure level
     */
    public static int computeTimeReductionFor(int lure) {
        // Handle no lure, which should offer no time reduction (100 to 600 ticks)
        if(lure == 0) {
            return 0;
        }

        int cap = 599; // Max reduction value from logistic function, 599 means the max can never be 0 (lowest = 1)
        int c = 33;
        double k = 0.0014;
        double val = cap / (1 + c * Math.pow(Math.E, -1 - k * cap * lure));
        return (int) val;
    }
}
