import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;

/**
 * Manages the weekly recipes. Storage is a LinkedChain<Recipe>, used only
 * through its public methods. Duplicate recipes are allowed.
 */
public class MealPlan {

    private final LinkedChain<Recipe> recipes;

    /** Creates an empty meal plan. */
    public MealPlan() {
        recipes = new LinkedChain<>();
    }

    /**
     * Adds one recipe (duplicates allowed).
     *
     * @param recipe the recipe to add
     * @return true if added
     * @throws IllegalArgumentException if recipe is null
     */
    public boolean add(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null.");
        }
        return recipes.add(recipe);
    }

    /**
     * Removes one recipe with the given name.
     *
     * @param name the recipe name (cleaned before use)
     * @return true if removed, false if no match
     * @throws IllegalArgumentException if the name is invalid
     */
    public boolean remove(String name) {
        String clean = Validator.cleanName(name);
        Recipe match = null;
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(clean)) {
                match = recipe;
                break;
            }
        }
        if (match == null) {
            return false;
        }
        // Recipe equality is by name, so this removes one entry with that name
        return recipes.remove(match);
    }

    /** @return an unmodifiable snapshot of the recipes, empty list if empty */
    public List<Recipe> getRecipes() {
        List<Recipe> copy = new ArrayList<>();
        for (Recipe recipe : recipes) {
            copy.add(recipe);
        }
        return Collections.unmodifiableList(copy);
    }

    /**
     * Builds a shopping list containing each ingredient needed by the meal
     * plan that is not currently in the pantry. Ingredients shared by
     * multiple recipes appear only once.
     *
     * @param pantry the pantry to check
     * @return an unmodifiable list of missing ingredient names
     * @throws IllegalArgumentException if pantry is null
     */
    public List<String> getShoppingList(Pantry pantry) {
        if (pantry == null) {
            throw new IllegalArgumentException("Pantry cannot be null.");
        }
        Set<String> missing = new LinkedHashSet<>();
        for (Recipe recipe : recipes) {
            for (String ingredient : recipe.getIngredients()) {
                if (!pantry.containsUnexpired(ingredient, LocalDate.now())) {
                    missing.add(ingredient);
                }
            }
        }
        return Collections.unmodifiableList(new ArrayList<>(missing));
    }
}
