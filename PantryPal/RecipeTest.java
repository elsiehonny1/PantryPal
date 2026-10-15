import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Tests for Recipe. */
public class RecipeTest extends student.TestCase {

    private Recipe omelette;

    public void setUp() {
        omelette = new Recipe(" Omelette ", Arrays.asList("Eggs", "cheese"));
    }

    public void testConstructor() {
        assertEquals("omelette", omelette.getName());
        assertEquals(Arrays.asList("eggs", "cheese"), omelette.getIngredients());
    }

    public void testOneIngredient() {
        Recipe r = new Recipe("toast", Arrays.asList("bread"));
        assertEquals("toast (ingredients: bread)", r.toString());
    }

    public void testIngredientsUnmodifiable() {
        try {
            omelette.getIngredients().add("salt");
            fail("should have thrown");
        }
        catch (UnsupportedOperationException e) {
            assertEquals(2, omelette.getIngredients().size());
        }
    }

    public void testDefensiveCopy() {
        List<String> list = new ArrayList<>(Arrays.asList("eggs"));
        Recipe r = new Recipe("scramble", list);
        list.add("salt");
        assertEquals(1, r.getIngredients().size());
    }

    public void testBadName() {
        try {
            new Recipe("", Arrays.asList("eggs"));
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testNullOrEmptyIngredients() {
        try {
            new Recipe("x", null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
        try {
            new Recipe("x", new ArrayList<String>());
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testBadIngredientEntry() {
        try {
            new Recipe("x", Arrays.asList("eggs", null));
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testEquals() {
        assertTrue(omelette.equals(new Recipe("omelette", Arrays.asList("ham"))));
        assertFalse(omelette.equals(new Recipe("pancakes", Arrays.asList("eggs", "cheese"))));
        assertTrue(omelette.equals(omelette));
        assertFalse(omelette.equals(null));
        assertFalse(omelette.equals("omelette"));
    }

    public void testHashCode() {
        assertEquals(omelette.hashCode(),
            new Recipe("omelette", Arrays.asList("ham")).hashCode());
    }

    public void testToString() {
        assertEquals("omelette (ingredients: eggs, cheese)", omelette.toString());
    }
}
