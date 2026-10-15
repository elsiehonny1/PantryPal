package PantryPal;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import PantryPal.src.MealPlan;
import PantryPal.src.Pantry;
 
/**
 * Runs the Pantry Pal program: shows a numbered menu, reads what the user
 * types, re-asks when input is bad, hands clean input to the Pantry and
 * MealPlan, prints the results, and exits cleanly on Quit or closed input.
 * 
 * Author: Blake Adkins
 * Date: 10/14/2026
 */
public class PantryPal {
 
    private final Pantry pantry;
    private final MealPlan mealPlan;
    private final Scanner input;
    private boolean running;
 
    /** Creates empty collections and terminal input; running starts false. */
    public PantryPal() {
        pantry = new Pantry();
        mealPlan = new MealPlan();
        input = new Scanner(System.in);
        running = false;
    }
 
    /** Repeats the menu until the user picks Quit or input is closed. */
    public void run() {
        running = true;
        while (running) {
            printMenu();
            String choice = readLine("Choose an option: ");
            if (choice != null) {
                handleChoice(choice.trim());
            }
        }
    }
 
    /** Starts the program. Arguments are unused. */
    public static void main(String[] args) {
        new PantryPal().run();
    }
 
    // ---------- menu ----------
 
    private void printMenu() {
        System.out.println();
        System.out.println("===== Pantry Pal =====");
        System.out.println("1. Add food");
        System.out.println("2. Remove food");
        System.out.println("3. Count a food");
        System.out.println("4. View pantry");
        System.out.println("5. Add recipe");
        System.out.println("6. Remove recipe");
        System.out.println("7. View meal plan");
        System.out.println("8. Quit");
    }
 
    private void handleChoice(String choice) {
        switch (choice) {
            case "1":
                addFood();
                break;
            case "2":
                removeFood();
                break;
            case "3":
                countFood();
                break;
            case "4":
                viewPantry();
                break;
            case "5":
                addRecipe();
                break;
            case "6":
                removeRecipe();
                break;
            case "7":
                viewMealPlan();
                break;
            case "8":
                System.out.println("Goodbye!");
                running = false;
                break;
            default:
                System.out.println("Please enter a valid menu number.");
                break;
        }
    }
 
    // ---------- pantry actions ----------
 
    private void addFood() {
        String name = promptName("Food name: ");
        if (name == null) {
            return;
        }
        LocalDate date = promptDate("Expiration date (MM/DD/YYYY): ");
        if (date == null) {
            return;
        }
        if (Validator.isPast(date, LocalDate.now())) {
            System.out.println("This item is already expired.");
        }
        pantry.add(new FoodItem(name, date));
        System.out.println("Added " + name + ".");
    }
 
    private void removeFood() {
        String name = promptName("Food name to remove: ");
        if (name == null) {
            return;
        }
        if (pantry.remove(name)) {
            System.out.println("Removed one " + name + " (soonest expiring).");
        } else {
            System.out.println("Item not found.");
        }
    }
 
    private void countFood() {
        String name = promptName("Food name to count: ");
        if (name == null) {
            return;
        }
        System.out.println("You have " + pantry.count(name) + " of " + name + ".");
    }
 
    private void viewPantry() {
        List<FoodItem> items = pantry.getItems();
        if (items.isEmpty()) {
            System.out.println("Your pantry is empty.");
            return;
        }
        for (FoodItem item : items) {
            System.out.println("- " + item);
        }
        System.out.println("Total items: " + pantry.size());
    }
 
    // ---------- meal plan actions ----------
 
    private void addRecipe() {
        String name = promptName("Recipe name: ");
        if (name == null) {
            return;
        }
        List<String> ingredients = promptIngredients("Ingredients (comma-separated): ");
        if (ingredients == null) {
            return;
        }
        mealPlan.add(new Recipe(name, ingredients));
        System.out.println("Added recipe " + name + ".");
    }
 
    private void removeRecipe() {
        String name = promptName("Recipe name to remove: ");
        if (name == null) {
            return;
        }
        if (mealPlan.remove(name)) {
            System.out.println("Removed recipe " + name + ".");
        } else {
            System.out.println("Item not found.");
        }
    }
 
    private void viewMealPlan() {
        List<Recipe> recipes = mealPlan.getRecipes();
        if (recipes.isEmpty()) {
            System.out.println("Your meal plan is empty.");
            return;
        }
        for (Recipe recipe : recipes) {
            System.out.println("- " + recipe);
        }
    }
 
    // ---------- input helpers ----------
 
    /**
     * Prints a prompt and reads one line. If input has been closed (Ctrl+D),
     * prints "Goodbye!", stops the program, and returns null.
     */
    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!input.hasNextLine()) {
            System.out.println("Goodbye!");
            running = false;
            return null;
        }
        return input.nextLine();
    }
 
    /** Asks until the user types a valid name. Returns null if input closed. */
    private String promptName(String prompt) {
        while (true) {
            String line = readLine(prompt);
            if (line == null) {
                return null;
            }
            try {
                return Validator.cleanName(line);
            } catch (IllegalArgumentException e) {
                // \u2013 is the en dash in "1-50"
                System.out.println("Name must be 1\u201350 characters with a letter or number.");
            }
        }
    }
 
    /** Asks until the user types a valid date. Returns null if input closed. */
    private LocalDate promptDate(String prompt) {
        while (true) {
            String line = readLine(prompt);
            if (line == null) {
                return null;
            }
            try {
                return Validator.parseDate(line);
            } catch (IllegalArgumentException e) {
                System.out.println("Date must be MM/DD/YYYY.");
            }
        }
    }
 
    /** Asks until the user types a valid ingredient list. Returns null if input closed. */
    private List<String> promptIngredients(String prompt) {
        while (true) {
            String line = readLine(prompt);
            if (line == null) {
                return null;
            }
            try {
                return Validator.parseIngredients(line);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
