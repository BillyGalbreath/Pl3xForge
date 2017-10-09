package net.pl3x.forge.client.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {
    public static double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
