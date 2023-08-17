package com.example.appbanlinhkien30.util;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Convert {
    public static String convertCurrencyStringToNumber(String currencyString) {
        String numericString = currencyString.replaceAll("[^0-9]", "");
        double numericValue = Integer.parseInt(numericString);
        return String.valueOf(numericValue);
    }

    public static String convertNumberToCurrencyString(double numericValue){
            Locale locale = new Locale("vi", "VN");
            Currency currency = Currency.getInstance("VND");

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
            currencyFormat.setCurrency(currency);

            return currencyFormat.format(numericValue);
    }
}
