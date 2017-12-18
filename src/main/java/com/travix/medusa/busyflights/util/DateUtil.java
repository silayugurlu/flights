package com.travix.medusa.busyflights.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date Utility to make Date operations
 *
 * @author silay
 */
public class DateUtil {

    /**
     * Converts date from origin format to target.
     *
     * @param origin
     * @param target
     * @param date
     * @return the formatted date string
     */
    public static String formatDate(DateTimeFormatter origin, DateTimeFormatter target, String date) {
        LocalDate localDate = LocalDate.parse(date, origin);
        return localDate.format(target);
    }

    /**
     * Converts date from origin format to target.
     *
     * @param origin
     * @param target
     * @param date
     * @return the formatted date string
     */
    public static String formatDateTime(DateTimeFormatter origin, DateTimeFormatter target, String date) {
        LocalDateTime localDate = LocalDateTime.parse(date, origin);
        return localDate.format(target);
    }

    /**
     * Checks if value is in the given format
     *
     * @param formatter
     * @param date
     * @return true/false
     */
    public static boolean isValidFormat(DateTimeFormatter formatter, String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if firstDate is before the secondDate.
     *
     * @param formatterOfFirst
     * @param firstDate
     * @param formatterOfSecond
     * @param secondDate
     * @return true if firstDate is before the secondDate
     */
    public static boolean isBeforeOrEqual(DateTimeFormatter formatterOfFirst, String firstDate, DateTimeFormatter formatterOfSecond, String secondDate) {
        LocalDate first = LocalDate.parse(firstDate, formatterOfFirst);
        LocalDate second = LocalDate.parse(secondDate, formatterOfSecond);
        return first.isBefore(second) || first.isEqual(second);
    }

    /**
     * Checks if the date is after now.
     *
     * @param formatter
     * @param date
     * @return true if date is after now
     */
    public static boolean isAfterOrEqualNow(DateTimeFormatter formatter, String date) {
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDate now = LocalDate.now();
        return localDate.isAfter(now) || localDate.isEqual(now);
    }
}
