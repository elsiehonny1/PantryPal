/** Tests for the PantryPal menu. Fakes keyboard input with setSystemIn. */
public class PantryPalTest extends student.TestCase {

    private String runWith(String keys) {
        setSystemIn(keys);
        PantryPal app = new PantryPal();
        app.run();
        return systemOut().getHistory();
    }

    public void testMenuPrintsAndQuit() {
        String out = runWith("7\n");
        assertTrue(out.contains("1. Add food"));
        assertTrue(out.contains("7. Quit"));
        assertTrue(out.contains("Goodbye!"));
    }

    public void testBadMenuChoices() {
        String out = runWith("9\nabc\n\n0\n-1\n7\n");
        assertTrue(out.contains("Please enter a valid menu number."));
        assertTrue(out.contains("Goodbye!"));
    }

    public void testBadNameThenGood() {
        String out = runWith("1\n\nmilk\n12/31/2099\n3\n7\n");
        assertTrue(out.contains("Name must be 1"));
        assertTrue(out.contains("milk (expires 12/31/2099)"));
    }

    public void testBadDateThenGood() {
        String out = runWith("1\nmilk\nabc\n12/31/2099\n7\n");
        assertTrue(out.contains("Date must be MM/DD/YYYY."));
        assertTrue(out.contains("Added milk."));
    }

    public void testPastDateWarns() {
        String out = runWith("1\nmilk\n01/01/2020\n7\n");
        assertTrue(out.contains("This item is already expired."));
        assertTrue(out.contains("Added milk."));
    }

    public void testAddThenViewFood() {
        String out = runWith("1\nMilk\n12/31/2099\n3\n7\n");
        assertTrue(out.contains("milk (expires 12/31/2099)"));
    }

    public void testRemoveFood() {
        String out = runWith("1\nmilk\n12/31/2099\n2\nmilk\n3\n7\n");
        assertTrue(out.contains("Removed milk"));
        assertTrue(out.contains("Your pantry is empty."));
    }

    public void testRemoveFoodNotFound() {
        String out = runWith("2\nmilk\n7\n");
        assertTrue(out.contains("Item not found."));
    }

    public void testRecipeAddRemoveView() {
        String out = runWith("4\nomelette\neggs, cheese\n6\n5\nomelette\n6\n7\n");
        assertTrue(out.contains("omelette (ingredients: eggs, cheese)"));
        assertTrue(out.contains("Removed omelette."));
        assertTrue(out.contains("Your meal plan is empty."));
    }

    public void testRecipeBadIngredientsThenGood() {
        String out = runWith("4\nomelette\n,,\neggs\n7\n");
        assertTrue(out.contains("A recipe needs at least one ingredient."));
        assertTrue(out.contains("Added omelette."));
    }

    public void testRemoveRecipeNotFound() {
        String out = runWith("5\nlasagna\n7\n");
        assertTrue(out.contains("Item not found."));
    }

    public void testInputClosedAtMenu() {
        String out = runWith("");
        assertTrue(out.contains("Goodbye!"));
    }

    public void testInputClosedMidPrompt() {
        String out = runWith("1\nmilk\n");
        assertTrue(out.contains("Goodbye!"));
    }

    public void testMain() {
        setSystemIn("7\n");
        PantryPal.main(new String[0]);
        assertTrue(systemOut().getHistory().contains("Goodbye!"));
    }
}
