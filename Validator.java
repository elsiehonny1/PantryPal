package PantryPal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Cleans and checks user input for Pantry Pal.
 * Author: Blake Adkins
 * Date: 10/14/2026
 */
public class Validator {

    /** Longest allowed food or recipe name, after trimming. */
    private static final int MAX_NAME_LENGTH = 50;

    /** MM/DD/YYYY format. */
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Trims and lowercases a name, then checks that it is valid.
     *
     * @throws IllegalArgumentException if null, blank, longer than 50 characters,
     *                                  or containing no letter or digit
     */
    public static String cleanName(String rawName) {
        if (rawName == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        String name = rawName.trim().toLowerCase();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "Name cannot be longer than " + MAX_NAME_LENGTH + " characters.");
        }
        boolean hasLetterOrDigit = false;
        for (int i = 0; i < name.length(); i++) {
            if (Character.isLetterOrDigit(name.charAt(i))) {
                hasLetterOrDigit = true;
                break;
            }
        }
        if (!hasLetterOrDigit) {
            throw new IllegalArgumentException("Name must contain a letter or a number.");
        }
        return name;
    }

    /**
     * Strictly parses a date in MM/DD/YYYY format.
     *
     * @throws IllegalArgumentException if null, incorrect format, or not a real calendar date
     */
    public static LocalDate parseDate(String rawDate) {
        if (rawDate == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        String trimmed = rawDate.trim();
        if (!trimmed.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new IllegalArgumentException("Date must be MM/DD/YYYY.");
        }
        LocalDate date;
        try {
            date = LocalDate.parse(trimmed, DATE_FORMAT);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Date must be MM/DD/YYYY.", e);
        }
        // The default parser quietly "fixes" dates like 02/30/2026 to 02/28/2026.
        // Formatting the result back and comparing catches that.
        if (!date.format(DATE_FORMAT).equals(trimmed)) {
            throw new IllegalArgumentException("Date must be MM/DD/YYYY.");
        }
        return date;
    }

    /**
     * Checks whether a date is before today. Today itself is not past.
     *
     * @throws IllegalArgumentException if either argument is null
     */
    public static boolean isPast(LocalDate date, LocalDate today) {
        if (date == null || today == null) {
            throw new IllegalArgumentException("Dates cannot be null.");
        }
        return date.isBefore(today);
    }

    /**
     * Splits a comma-separated ingredient list, skipping blank entries and
     * normalizing each remaining name with cleanName.
     *
     * @throws IllegalArgumentException if null, if the result would be empty,
     *                                  or if any non-blank entry is an invalid name
     */
    public static List<String> parseIngredients(String rawIngredients) {
        if (rawIngredients == null) {
            throw new IllegalArgumentException("Ingredients cannot be null.");
        }
        List<String> ingredients = new ArrayList<>();
        for (String piece : rawIngredients.split(",")) {
            if (!piece.trim().isEmpty()) {
                ingredients.add(cleanName(piece));
            }
        }
        if (ingredients.isEmpty()) {
            throw new IllegalArgumentException("A recipe needs at least one ingredient.");
        }
        return ingredients;
    }
}