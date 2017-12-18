package com.travix.medusa.busyflights.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Utility to make numeric operations
 *
 * @author silay
 */
public class NumberUtil {

    /**
     * Rounds value to 2 decimals
     *
     * @param value
     * @return rounded value
     */
    public static String formatFare(double value) {
        NumberFormat numberFormat = new DecimalFormat("#0.00");
        return numberFormat.format(value);
    }
}
