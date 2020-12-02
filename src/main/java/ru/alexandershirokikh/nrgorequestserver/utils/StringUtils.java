package ru.alexandershirokikh.nrgorequestserver.utils;

public final class StringUtils {

    /**
     * Simple roman number converter.
     * Note: Works only in range 1..5 !
     */
    public static String toRoman(int number) {
        switch (number) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
        }
        throw new IllegalArgumentException("Unsupported conversion: " + number);
    }
}
