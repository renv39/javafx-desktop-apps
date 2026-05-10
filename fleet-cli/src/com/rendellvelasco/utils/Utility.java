package com.rendellvelasco.utils;

import java.util.Scanner;

public class Utility {

    private static final Scanner SC = new Scanner(System.in); // Keep a single scanner

    // Takes input and returns a value depending on the flag
    public static Object takeInput(boolean isInteger) {
        while (true) {
            String input = SC.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Error: Input cannot be empty.");
                continue;
            }

            if (isInteger) {
                Integer mileage = parseInteger(input);
                if (mileage != null) return mileage; // valid mileage
            } else {
                String category = parseCategory(input);
                if (category != null) return category; // valid category
            }
        }
    }

    // Parse integer input safely
    private static Integer parseInteger(String input) {
        try {
            int mileage = Integer.parseInt(input);
            if (mileage < 0) {
                System.out.println("Error: Mileage cannot be negative.");
                return null;
            }
            System.out.println(mileage);
            return mileage;
        } catch (NumberFormatException e) {
            System.out.println("Error: Mileage must be a whole number.");
            return null;
        }
    }

    // Parse enum safely
    private static String parseCategory(String input) {
        if (CarCategory.isValid(input)) {

            CarCategory c = CarCategory.valueOf(input.toUpperCase());
            String category = c.name();
            return input;
        } else {
            System.out.println("Error: Category must be one of the listed choices.");
            return null;
        }
    }
}
