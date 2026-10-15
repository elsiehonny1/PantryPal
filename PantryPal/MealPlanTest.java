import java.util.Arrays;
import java.util.List;

/**
 * Tests for MealPlan. Normal + bad-input case for every public method,
 * plus empty-plan and removing-something-missing edge cases.
 */
public class MealPlanTest extends student.TestCase {

    private MealPlan plan;
    private Recipe omelette;
    private Recipe pancakes;

    public void setUp() {
        plan = new MealPlan();
        omelette = new Recipe("omelette", Arrays.asList("eggs", "cheese"));
        pancakes = new Recipe("pancakes", Arrays.asList("flour", "milk", "eggs"));
    }

    // ---------- empty ----------

    public void testEmptyPlan() {
        assertTrue(plan.getRecipes().isEmpty());
        assertFalse(plan.remove("omelette"));
    }

    // ---------- add ----------

    public void testAddNormal() {
        assertTrue(plan.add(omelette));
        assertEquals(1, plan.getRecipes().size());
        assertTrue(plan.getRecipes().contains(omelette));
    }

    public void testAddNull() {
        try {
            plan.add(null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertTrue(plan.getRecipes().isEmpty());
        }
    }

    public void testAddDuplicateAllowed() {
        plan.add(omelette);
        plan.add(omelette);
        assertEquals(2, plan.getRecipes().size());
    }

    // ---------- remove ----------

    public void testRemoveNormal() {
        plan.add(omelette);
        plan.add(pancakes);
        assertTrue(plan.remove("omelette"));
        assertEquals(1, plan.getRecipes().size());
        assertFalse(plan.getRecipes().contains(omelette));
    }

    public void testRemoveCleansName() {
        plan.add(omelette);
        assertTrue(plan.remove("  OMELETTE "));
        assertTrue(plan.getRecipes().isEmpty());
    }

    public void testRemoveOnlyOneDuplicate() {
        plan.add(omelette);
        plan.add(omelette);
        assertTrue(plan.remove("omelette"));
        assertEquals(1, plan.getRecipes().size());
    }

    public void testRemoveMissing() {
        plan.add(omelette);
        assertFalse(plan.remove("lasagna"));
        assertEquals(1, plan.getRecipes().size());
    }

    public void testRemoveBadName() {
        plan.add(omelette);
        try {
            plan.remove("");
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertEquals(1, plan.getRecipes().size());
        }
    }

    // ---------- getRecipes ----------

    public void testGetRecipesHasEverything() {
        plan.add(omelette);
        plan.add(pancakes);
        List<Recipe> list = plan.getRecipes();
        assertEquals(2, list.size());
        assertTrue(list.contains(omelette));
        assertTrue(list.contains(pancakes));
    }

    public void testGetRecipesIsUnmodifiable() {
        plan.add(omelette);
        try {
            plan.getRecipes().add(pancakes);
            fail("should have thrown");
        }
        catch (UnsupportedOperationException e) {
            assertEquals(1, plan.getRecipes().size());
        }
    }
}
