import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One recipe: a normalized name and a nonempty ingredient list. Immutable.
 * Two recipes are equal if their names match.
 */
public class Recipe {

    private final String name;
    private final List<String> ingredients;

    /** @throws IllegalArgumentException if name invalid, list null/empty, or any entry invalid */
    public Recipe(String name, List<String> ingredients) {
        this.name = Validator.cleanName(name);
        if (ingredients == null || ingredients.isEmpty()) {
            throw new IllegalArgumentException("A recipe needs at least one ingredient.");
        }
        List<String> copy = new ArrayList<>();
        for (String ingredient : ingredients) {
            copy.add(Validator.cleanName(ingredient));
        }
        this.ingredients = Collections.unmodifiableList(copy);
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return name.equals(((Recipe) other).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + " (ingredients: " + String.join(", ", ingredients) + ")";
    }
}
