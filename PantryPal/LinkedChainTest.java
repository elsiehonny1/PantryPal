import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;

/** Tests for LinkedChain, including reuse with two element types. */
public class LinkedChainTest extends student.TestCase {

    private LinkedChain<String> chain;

    public void setUp() {
        chain = new LinkedChain<>();
    }

    /** Items in iteration order, joined with commas. */
    private String contents() {
        StringBuilder sb = new StringBuilder();
        for (String s : chain) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public void testNewChain() {
        assertEquals(0, chain.size());
        assertTrue(chain.isEmpty());
    }

    public void testAddOne() {
        assertTrue(chain.add("a"));
        assertEquals(1, chain.size());
        assertFalse(chain.isEmpty());
        assertTrue(chain.contains("a"));
    }

    public void testAddDuplicates() {
        chain.add("a");
        chain.add("b");
        chain.add("a");
        assertEquals(3, chain.size());
        assertEquals(2, chain.count("a"));
        assertEquals(1, chain.count("b"));
    }

    public void testAddNull() {
        try {
            chain.add(null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertEquals(0, chain.size());
        }
    }

    public void testRemovePresent() {
        chain.add("a");
        chain.add("b");
        assertTrue(chain.remove("a"));
        assertEquals(1, chain.size());
        assertFalse(chain.contains("a"));
    }

    public void testRemoveFrontMiddleEnd() {
        chain.add("a");
        chain.add("b");
        chain.add("c");
        assertEquals("c,b,a", contents());
        assertTrue(chain.remove("b"));
        assertEquals("c,a", contents());
        assertTrue(chain.remove("c"));
        assertEquals("a", contents());
        assertTrue(chain.remove("a"));
        assertEquals("", contents());
        assertEquals(0, chain.size());
    }

    public void testRemoveEnd() {
        chain.add("a");
        chain.add("b");
        chain.add("c");
        assertTrue(chain.remove("a"));
        assertEquals("c,b", contents());
        assertEquals(2, chain.size());
    }

    public void testRemoveOneOfRepeats() {
        chain.add("a");
        chain.add("a");
        assertTrue(chain.remove("a"));
        assertEquals(1, chain.size());
        assertEquals(1, chain.count("a"));
    }

    public void testRemoveMissingAndEmpty() {
        assertFalse(chain.remove("a"));
        chain.add("b");
        assertFalse(chain.remove("a"));
        assertEquals(1, chain.size());
    }

    public void testContainsAndCountMissing() {
        chain.add("a");
        assertFalse(chain.contains("z"));
        assertEquals(0, chain.count("z"));
    }

    public void testNullHandling() {
        chain.add("a");
        assertFalse(chain.contains(null));
        assertEquals(0, chain.count(null));
        assertFalse(chain.remove(null));
        assertEquals(1, chain.size());
    }

    public void testIterate() {
        chain.add("a");
        chain.add("b");
        Iterator<String> it = chain.iterator();
        assertTrue(it.hasNext());
        assertEquals("b", it.next());
        assertEquals("a", it.next());
        assertFalse(it.hasNext());
    }

    public void testIterateEmpty() {
        assertFalse(chain.iterator().hasNext());
    }

    public void testNextPastEnd() {
        chain.add("a");
        Iterator<String> it = chain.iterator();
        it.next();
        try {
            it.next();
            fail("should have thrown");
        }
        catch (NoSuchElementException e) {
            assertNotNull(e);
        }
    }

    public void testIteratorRemoveUnsupported() {
        chain.add("a");
        Iterator<String> it = chain.iterator();
        it.next();
        try {
            it.remove();
            fail("should have thrown");
        }
        catch (UnsupportedOperationException e) {
            assertEquals(1, chain.size());
        }
    }

    // ---- same generic class, different element types ----

    public void testStoresFoodItems() {
        LinkedChain<FoodItem> foods = new LinkedChain<>();
        FoodItem milk = new FoodItem("milk", LocalDate.of(2026, 10, 20));
        foods.add(milk);
        FoodItem back = foods.iterator().next();
        assertEquals(milk, back);
        assertTrue(foods.contains(new FoodItem("Milk", LocalDate.of(2026, 10, 20))));
    }

    public void testStoresRecipes() {
        LinkedChain<Recipe> recipes = new LinkedChain<>();
        Recipe r = new Recipe("omelette", Arrays.asList("eggs"));
        recipes.add(r);
        assertEquals(r, recipes.iterator().next());
        assertEquals(1, recipes.count(new Recipe("omelette", Arrays.asList("cheese"))));
    }
}
