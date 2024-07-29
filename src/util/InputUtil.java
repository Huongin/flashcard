package src.util;

import src.constant.CommonConstant;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InputUtil {
    public static int chooseOption(String message, String errorMessage, Integer minvalue, Integer maxValue) {
        System.out.print(message);
        int choice = -1;
        while (true) {
            try {
                choice = new Scanner(System.in).nextInt();
                if (
                        (minvalue == null || choice >= minvalue)
                                && (maxValue == null || choice <= maxValue)
                ) {
                    break;
                }
                System.out.print(errorMessage);
            } catch (InputMismatchException e) {
                System.out.print(errorMessage);
            }
        }
        return choice;
    }

    public static int chooseOption(String message, List<String> options, String errorMessage){
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ": " + options.get(i));
        }
        System.out.print(message);
        int choice = -1;
        while (true) {
            try {
                choice = new Scanner(System.in).nextInt();
                if (choice >= 1 && choice <= options.size()) {
                    break;
                }
                System.out.print(errorMessage);
            } catch (InputMismatchException e) {
                System.out.print(errorMessage);
            }
        }
        return choice;
    }

    public static boolean exitInput(String input){
        return input == null || input.equalsIgnoreCase(CommonConstant.Exit_SIGNAL);
    }
}