import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Runs the program: shows the menu, reads input, re-asks on bad input,
 * and hands clean input to Pantry and MealPlan.
 */
public class PantryPal {

    private final Pantry pantry;
    private final MealPlan mealPlan;
    private final Scanner input;
    private boolean running;

    /** Creates empty collections and a terminal reader. Call after System.in is set. */
    public PantryPal() {
        pantry = new Pantry();
        mealPlan = new MealPlan();
        input = new Scanner(System.in);
        running = false;
    }

    /** Repeats the menu until Quit or input closes. */
    public void run() {
        running = true;
        while (running) {
            printMenu();
            String choice = ask("Choose an option: ");
            if (choice == null) {
                break;
            }
            switch (choice.trim()) {
                case "1":
                    addFood();
                    break;
                case "2":
                    removeFood();
                    break;
                case "3":
                    viewPantry();
                    break;
                case "4":
                    addRecipe();
                    break;
                case "5":
                    removeRecipe();
                    break;
                case "6":
                    viewMealPlan();
                    break;
                case "7":
                    quit();
                    break;
                default:
                    System.out.println("Please enter a valid menu number.");
            }
        }
    }

    public static void main(String[] args) {
        new PantryPal().run();
    }

    // ---------- menu actions ----------

    private void addFood() {
        String name = askName("Food name: ");
        if (name == null) {
            return;
        }
        LocalDate date = askDate("Expiration date (MM/DD/YYYY): ");
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
        String name = askName("Food name to remove: ");
        if (name == null) {
            return;
        }
        if (pantry.remove(name)) {
            System.out.println("Removed " + name + " (soonest expiring).");
        }
        else {
            System.out.println("Item not found.");
        }
    }

    private void viewPantry() {
        List<FoodItem> foods = pantry.getItems();
        if (foods.isEmpty()) {
            System.out.println("Your pantry is empty.");
            return;
        }
        for (FoodItem food : foods) {
            System.out.println(food);
        }
    }

    private void addRecipe() {
        String name = askName("Recipe name: ");
        if (name == null) {
            return;
        }
        List<String> ingredients = askIngredients("Ingredients (comma-separated): ");
        if (ingredients == null) {
            return;
        }
        mealPlan.add(new Recipe(name, ingredients));
        System.out.println("Added " + name + ".");
    }

    private void removeRecipe() {
        String name = askName("Recipe name to remove: ");
        if (name == null) {
            return;
        }
        if (mealPlan.remove(name)) {
            System.out.println("Removed " + name + ".");
        }
        else {
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
            System.out.println(recipe);
        }
    }

    // ---------- input helpers (return null if input closed) ----------

    private void printMenu() {
        System.out.println();
        System.out.println("=== PantryPal ===");
        System.out.println("1. Add food");
        System.out.println("2. Remove food");
        System.out.println("3. View pantry");
        System.out.println("4. Add recipe");
        System.out.println("5. Remove recipe");
        System.out.println("6. View meal plan");
        System.out.println("7. Quit");
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        if (!input.hasNextLine()) {
            quit();
            return null;
        }
        return input.nextLine();
    }

    private void quit() {
        if (running) {
            System.out.println("Goodbye!");
            running = false;
        }
    }

    private String askName(String prompt) {
        while (true) {
            String raw = ask(prompt);
            if (raw == null) {
                return null;
            }
            try {
                return Validator.cleanName(raw);
            }
            catch (IllegalArgumentException e) {
                System.out.println("Name must be 1-50 characters with a letter or number.");
            }
        }
    }

    private LocalDate askDate(String prompt) {
        while (true) {
            String raw = ask(prompt);
            if (raw == null) {
                return null;
            }
            try {
                return Validator.parseDate(raw);
            }
            catch (IllegalArgumentException e) {
                System.out.println("Date must be MM/DD/YYYY.");
            }
        }
    }

    private List<String> askIngredients(String prompt) {
        while (true) {
            String raw = ask(prompt);
            if (raw == null) {
                return null;
            }
            try {
                return Validator.parseIngredients(raw);
            }
            catch (IllegalArgumentException e) {
                System.out.println("A recipe needs at least one ingredient.");
            }
        }
    }
}
