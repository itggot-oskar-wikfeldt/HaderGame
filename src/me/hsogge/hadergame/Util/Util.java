package me.hsogge.hadergame.util;

/**
 * Created by oskar.wikfeldt on 2017-03-23.
 */
public class Util {

    public static float decrement(float val1, float val2) {
        if (val1 > 0) {
            val1 -= val2;
            if (val1 < 0) {
                return 0;
            }
            return val1;
        } else if (val1 < 0) {
            val1 += val2;
            if (val1 > 0) {
                return 0;
            }
            return val1;
        }
        return 0;
    }

}
