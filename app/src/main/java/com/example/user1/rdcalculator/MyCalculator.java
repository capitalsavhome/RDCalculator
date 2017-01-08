package com.example.user1.rdcalculator;

/**
 * Created by user1 on 07.01.2017.
 */

public class MyCalculator {

    public final static String ERROR_DIV_BY_ZERO = "Error division by zero!";

    public static String makeSum(String firstValue, String secondValue) {
        double first = Double.parseDouble(firstValue);
        double second = Double.parseDouble(secondValue);

        return Double.toString(first+second);
    }

    public static String makeMinus(String firstValue, String secondValue) {
        double first = Double.parseDouble(firstValue);
        double second = Double.parseDouble(secondValue);

        return Double.toString(first-second);
    }

    public static String makeMultiple(String firstValue, String secondValue) {
        double first = Double.parseDouble(firstValue);
        double second = Double.parseDouble(secondValue);

        return Double.toString(first*second);
    }

    public static String makeDivision(String firstValue, String secondValue) {
        double first = Double.parseDouble(firstValue);
        double second = Double.parseDouble(secondValue);

        if (second == 0) {
            return ERROR_DIV_BY_ZERO;
        }
        else {
            return Double.toString(first/second);
        }
    }

    public static String makePerCent(String firstValue, String secondValue) {
        double first = Double.parseDouble(firstValue);
        double second = Double.parseDouble(secondValue);

        return Double.toString(first*second/100);
    }

}
