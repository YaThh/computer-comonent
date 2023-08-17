package com.example.appbanlinhkien30.util;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Convert {
    //Chuyển đổi chuỗi tiền tệ sang số thực
    public static String convertCurrencyStringToNumber(String currencyString) {
        String numericString = currencyString.replaceAll("[^0-9]", "");
        double numericValue = Double.parseDouble(numericString);
        return String.valueOf(numericValue);
    }

    //Chuyển đổi số thực sang chuỗi tiền tệ
    public static String convertNumberToCurrencyString(double numericValue){
            Locale locale = new Locale("vi", "VN");
            Currency currency = Currency.getInstance("VND");

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
            currencyFormat.setCurrency(currency);

            return currencyFormat.format(numericValue);
    }
}
