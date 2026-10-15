import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the food items in the pantry. Items are stored in a LinkedChain.
 * Name-based methods match by name only, across all expiration dates.
 *
 * @author Ayaan Deshmukh
 * @version Oct 14, 2026
 */
public class Pantry
{
    private final LinkedChain<FoodItem> items;

    /**
     * Creates an empty pantry.
     */
    public Pantry()
    {
        items = new LinkedChain<FoodItem>();
    }


    /**
     * Adds one food item to the pantry.
     *
     * @param item
     *            the food item to add
     * @return true when the item is added
     * @throws IllegalArgumentException
     *             if item is null
     */
    public boolean add(FoodItem item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Food item cannot be null.");
        }
        return items.add(item);
    }


    /**
     * Removes one food item with the given name. If there are several, the
     * one with the earliest expiration date is removed.
     *
     * @param name
     *            the name of the food to remove
     * @return true if an item was removed, false if none matched
     * @throws IllegalArgumentException
     *             if the name is invalid
     */
    public boolean remove(String name)
    {
        String cleaned = Validator.cleanName(name);
        FoodItem soonest = null;
        for (FoodItem item : items)
        {
            if (item.getName().equals(cleaned) && (soonest == null
                || item.getExpirationDate().isBefore(
                    soonest.getExpirationDate())))
            {
                soonest = item;
            }
        }
        if (soonest == null)
        {
            return false;
        }
        return items.remove(soonest);
    }


    /**
     * Checks whether any food item has the given name.
     *
     * @param name
     *            the name to look for
     * @return true if at least one item matches
     * @throws IllegalArgumentException
     *             if the name is invalid
     */
    public boolean contains(String name)
    {
        return count(name) > 0;
    }


    /**
     * Counts the food items with the given name, across all expiration
     * dates.
     *
     * @param name
     *            the name to count
     * @return the number of matching items, or 0 if none
     * @throws IllegalArgumentException
     *             if the name is invalid
     */
    public int count(String name)
    {
        String cleaned = Validator.cleanName(name);
        int total = 0;
        for (FoodItem item : items)
        {
            if (item.getName().equals(cleaned))
            {
                total++;
            }
        }
        return total;
    }


    /**
     * Returns the total number of food items, including duplicates.
     *
     * @return the number of items in the pantry
     */
    public int size()