package net.pl3x.forge.util;

import net.minecraft.util.StringUtils;

public class Validator {
    public static boolean predicateInteger(String input) {
        if (StringUtils.isNullOrEmpty(input)) {
            return true;
        }
        try {
            return Integer.valueOf(input) != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean predicateDouble(String input) {
        if (StringUtils.isNullOrEmpty(input)) {
            return true;
        }
        try {
            return Double.valueOf(input) != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
