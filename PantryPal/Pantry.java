import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the pantry's food items. All storage is done by a
 * LinkedChain<FoodItem>; this class only calls the chain's public methods.
 *
 * Name-based methods clean the name with Validator.cleanName, so an invalid
 * name throws IllegalArgumentException, while a valid name that isn't in the
 * pantry returns false or 0.
 */
public class Pantry {

    private final LinkedChain<FoodItem> items;

    /** Creates an empty pantry. */
    public Pantry() {
        items = new LinkedChain<>();
    }

    /**
     * Adds one food item.
     *
     * @param item the food to add
     * @return true if added
     * @throws IllegalArgumentException if item is null
     */
    public boolean add(FoodItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Food item cannot be null.");
        }
        return items.add(item);
    }

    /**
     * Removes ONE item with the given name, choosing the one that expires
     * soonest.
     *
     * @param name the food name (cleaned before use)
     * @return true if something was removed, false if no match
     * @throws IllegalArgumentException if the name is invalid
     */
    public boolean remove(String name) {
        String clean = Validator.cleanName(name);
        FoodItem soonest = null;
        for (FoodItem food : items) {
            if (food.getName().equals(clean)) {
                if (soonest == null || food.getExpirationDate()
                    .isBefore(soonest.getExpirationDate())) {
                    soonest = food;
                }
            }
        }
        if (soonest == null) {
            return false;
        }
        // equal items (same name + date) are interchangeable, so removing
        // one equal entry is the same as removing the one we found
        return items.remove(soonest);
    }

    /**
     * Checks whether any item with this name exists.
     *
     * @param name the food name (cleaned before use)
     * @return true if at least one match
     * @throws IllegalArgumentException if the name is invalid
     */
    public boolean contains(String name) {
        return count(name) > 0;
    }

    /**
     * Counts items with this name across all expiration dates.
     *
     * @param name the food name (cleaned before use)
     * @return number of matches, 0 if none
     * @throws IllegalArgumentException if the name is invalid
     */
    public int count(String name) {
        String clean = Validator.cleanName(name);
        int total = 0;
        for (FoodItem food : items) {
            if (food.getName().equals(clean)) {
                total++;
            }
        }
        return total;
    }

    /** @return total number of food entries, duplicates included */
    public int size() {
        return items.size();
    }

    /** @return an unmodifiable snapshot of the pantry, empty list if empty */
    public List<FoodItem> getItems() {
        List<FoodItem> copy = new ArrayList<>();
        for (FoodItem food : items) {
            copy.add(food);
        }
        return Collections.unmodifiableList(copy);
    }
}
