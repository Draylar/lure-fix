package draylar;

import draylar.lurefix.logic.FishingBobberLogic;
import net.minecraft.util.math.MathHelper;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FishingBobberTests {

    @Test
    public void testLogisticMethod() {
        assertEquals(0, FishingBobberLogic.computeTimeReductionFor(0));

        int levelOneReduction = FishingBobberLogic.computeTimeReductionFor(1); // should be ~100
        assertTrue(levelOneReduction > 80 && levelOneReduction < 125);

        int levelTwoReduction = FishingBobberLogic.computeTimeReductionFor(2); // should be ~200
        assertTrue(levelTwoReduction > 180 && levelTwoReduction < 225);

        int levelThreeReduction = FishingBobberLogic.computeTimeReductionFor(3); // should be ~300
        assertTrue(levelThreeReduction > 280 && levelThreeReduction < 325);

        int levelFourReduction = FishingBobberLogic.computeTimeReductionFor(4); // should be ~400
        assertTrue(levelFourReduction > 380 && levelFourReduction < 425);

        int levelFiveReduction = FishingBobberLogic.computeTimeReductionFor(5); // should be ~500
        assertTrue(levelFiveReduction > 480 && levelFiveReduction < 525);
    }

    @Test
    public void testVanillaLikeness() {
        Random random = new Random();

        // lure 0 is from 100 to 600 ticks
        int time0 = getTimeFor(random, 0);
        assertTrue(time0 >= 100 && time0 <= 600);

        // lure 1 is from 0 to 500 ticks
        int time1 = getTimeFor(random, 1);
        assertTrue(time1 >= 0 && time1 <= 500);

        // lure 2 is from -100 to 400 ticks
        int time2 = getTimeFor(random, 2);
        assertTrue(time2 >= 0 && time2 <= 400);

        // lure 3 is from -200 to 300 ticks
        int time3 = getTimeFor(random, 3);
        assertTrue(time3 >= 0 && time3 <= 300);
    }

    private int getTimeFor(Random random, int lure) {
        int initialWaitCountdown = MathHelper.nextInt(random, 100, 600);
        initialWaitCountdown -= Math.min(initialWaitCountdown, FishingBobberLogic.computeTimeReductionFor(lure));
        return initialWaitCountdown;
    }
}
