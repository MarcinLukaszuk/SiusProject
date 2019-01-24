package com.example.marcin.siusproject;

public class Helper {

    public static String ParseDoubleToDecimalString(String input) {
        String output = input;

        if (output.indexOf('.') != -1) {
            output = output.substring(0, output.indexOf('.') + 2);
        }
        return output;
    }


}
