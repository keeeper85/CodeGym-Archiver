package com.codegym.task.task31.task3110;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String text;

        try {
            text = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text;
    }

    public static int readInt() {
        String text = readString();
        int number;
        try {
            number = Integer.parseInt(text);
            return number;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
