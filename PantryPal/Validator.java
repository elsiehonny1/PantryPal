import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cleans and checks user input. Throws IllegalArgumentException on bad input.
 */
public class Validator {

    static final int MAX_NAME_LENGTH = 50;
    static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private Validator() {
        // static helpers only
    }

    /**
     * Trims and lowercases a name.
     *
     * @throws IllegalArgumentException if null, blank, over 50 chars, or has no letter/digit
     */
    public static String cleanName(String rawName) {
        if (rawName == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        String name = rawName.trim().toLowerCase();
        if (name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must be 1-50 characters.");
        }
        boolean hasLetterOrDigit = name.codePoints().anyMatch(Character::isLetterOrDigit);
        if (!hasLetterOrDigit) {
            throw new IllegalArgumentException("Name needs a letter or number.");
        }
        return name;
    }

    /**
     * Strictly parses MM/DD/YYYY.
     *
     * @throws IllegalArgumentException if null, malformed, or impossible
     */
    public static LocalDate parseDate(String rawDate) {
        if (rawDate == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        try {
            return LocalDate.parse(rawDate.trim(), DATE_FORMAT);
        }
        catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date must be MM/DD/YYYY.");
        }
    }

    /**
     * True if date is before today (today itself is not past).
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
     * Splits on commas, skips blanks, cleans each name.
     *
     * @throws IllegalArgumentException if null, empty result, or an entry is invalid
     */
    public static List<String> parseIngredients(String rawIngredients) {
        if (rawIngredients == null) {
            throw new IllegalArgumentException("Ingredients cannot be null.");
        }
        List<String> result = new ArrayList<>();
        for (String piece : rawIngredients.split(",")) {
            if (!piece.trim().isEmpty()) {
                result.add(cleanName(piece));
            }
        }
        if (result.isEmpty()) {
            throw new IllegalArgumentException("A recipe needs at least one ingredient.");
        }
        return Collections.unmodifiableList(result);
    }
}
