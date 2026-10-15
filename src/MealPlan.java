import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the weekly meal plan. Recipes are stored in a LinkedChain, and
 * duplicate recipes are allowed.
 *
 * @author Ayaan Deshmukh
 * @version Oct 14, 2026
 */
public class MealPlan
{
    private final LinkedChain<Recipe> recipes;

    /**
     * Creates an empty meal plan.
     */
    public MealPlan()
    {
        recipes = new LinkedChain<Recipe>();
    }


    /**
     * Adds a recipe to the meal plan. Duplicates are allowed.
     *
     * @param recipe
     *            the recipe to add
     * @return true when the recipe is added
     * @throws IllegalArgumentException
     *             if recipe is null
     */
    public boolean add(Recipe recipe)
    {
        if (recipe == null)
        {
            throw new IllegalArgumentException("Recipe cannot be null.");
        }
        return recipes.add(recipe);
    }


    /**
     * Removes one recipe with the given name.
     *
     * @param name
     *            the name of the recipe to remove
     * @return true if a recipe was removed, false if none matched
     * @throws IllegalArgumentException
     *             if the name is invalid
     */
    public boolean remove(String name)
    {
        String cleaned = Validator.cleanName(name);
        Recipe match = null;
        for (Recipe recipe : recipes)
        {
            if (match == null && recipe.getName().equals(cleaned))
            {
                match = recipe;
            }
        }
        if (match == null)
        {
            return false;
        }
        return recipes.remove(match);
    }


    /**
     * Returns a read-only snapshot of every recipe in the meal plan.
     *
     * @return an unmodifiable list of recipes, empty if there are none
     */
    public List<Recipe> getRecipes()
    {
        List<Recipe> list = new ArrayList<Recipe>();
        for (Recipe recipe : recipes)
        {
            list.add(recipe);
        }
        return Collections.unmodifiableList(list);
    }
}