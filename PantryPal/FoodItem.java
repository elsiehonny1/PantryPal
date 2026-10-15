import java.time.LocalDate;
import java.util.Objects;

/**
 * One food item: a normalized name and an expiration date. Immutable.
 * Two foods are equal if name AND date match.
 */
public class FoodItem {

    private final String name;
    private final LocalDate expirationDate;

    /** @throws IllegalArgumentException if name is invalid or date is null */
    public FoodItem(String name, LocalDate expirationDate) {
        this.name = Validator.cleanName(name);
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date cannot be null.");
        }
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        FoodItem that = (FoodItem) other;
        return name.equals(that.name) && expirationDate.equals(that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, expirationDate);
    }

    @Override
    public String toString() {
        return name + " (expires " + expirationDate.format(Validator.DATE_FORMAT) + ")";
    }
}
