package com.chebdowski.tddtechnologyconversations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Piotr Chebdowski on 12.06.2017.
 */

public class StringCalculator {
    public static int add(String numbers) {
        String delimiters = ",|\n";

        if (numbers.startsWith("//")) {
            String newDelimiters = getDelimiters(numbers);
            delimiters += newDelimiters;
            numbers = numbers.substring(numbers.indexOf("\n") + 1);
        }

        return add(numbers, delimiters);
    }

    private static String getDelimiters(String value) {
        StringBuilder delimiters = new StringBuilder();
        String delimiterString = value.substring(2, value.indexOf("\n"));

        do {
            String del = delimiterString.substring(delimiterString.indexOf("[") + 1, delimiterString.indexOf("]"));
            delimiters.append("|");
            delimiters.append(del);
            delimiterString = delimiterString.substring(delimiterString.indexOf("]")+1);
        } while (delimiterString.contains("]"));

        return delimiters.toString();
    }

    private static int add(String numbers, String delimiter) {
        int value = 0;

        String stringNumbers[] = numbers.split(delimiter);
        checkForNegativeNumbers(stringNumbers);

        if (stringNumbers.length > 0) {
            value = Stream.of(stringNumbers)
                    .filter(s -> !s.equals(""))
                    .map(Integer::parseInt)
                    .filter(i -> i < 1000)
                    .reduce(0, Integer::sum);
        }

        return value;
    }

    private static void checkForNegativeNumbers(String stringNumbers[]) {
        List<Integer> negativeNumbers = new ArrayList();

        Stream.of(stringNumbers)
                .filter(s -> !s.equals(""))
                .map(Integer::parseInt)
                .filter(i -> i < 0)
                .forEach(negativeNumbers::add);

        if (negativeNumbers.size() > 0) {
            String message = "Negatives not allowed: ";
            String commaSeparatedNumbers = negativeNumbers.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ", "[", "]"));
            throw new RuntimeException(message + commaSeparatedNumbers);
        }
    }
}
