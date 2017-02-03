package rosko.bojan.rupko.game;

import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 2/3/17.
 */

public class LowPassFilter {
    private float alfa;
    private float lastValue;

    public LowPassFilter() {

        alfa = GameConfiguration.currentConfiguration.FILTER_ALFA;
        lastValue = 0;
    }

    public float filter(float value) {
        lastValue = alfa * value + (1 - alfa) * lastValue;
        return lastValue;
    }
}
