package com.example.marcin.siusproject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static String ParseDoubleToDecimalString(String input) {
        String output = input;

        if (output.indexOf('.') != -1) {
            output = output.substring(0, output.indexOf('.') + 2);
        }
        return output;
    }

    public static String GetDateStringFromDateString(String inputDate) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = parser.parse(inputDate);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(date);

        } catch (Exception ex) {
        }
        return inputDate;
    }
    public static String GetTimeStringFromDateString(String inputDate) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = parser.parse(inputDate);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            return formatter.format(date);

        } catch (Exception ex) {
        }
        return inputDate;
    }

}
