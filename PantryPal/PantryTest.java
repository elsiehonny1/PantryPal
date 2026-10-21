import java.time.LocalDate;
import java.util.List;

/**
 * Tests for Pantry. Normal + bad-input case for every public method,
 * plus empty-pantry and removing-something-missing edge cases.
 */
public class PantryTest extends student.TestCase {

    private Pantry pantry;
    private FoodItem milkSoon;
    private FoodItem milkLate;
    private FoodItem eggs;

    public void setUp() {
        pantry = new Pantry();
        milkSoon = new FoodItem("milk", LocalDate.of(2026, 10, 20));
        milkLate = new FoodItem("milk", LocalDate.of(2026, 10, 30));
        eggs = new FoodItem("eggs", LocalDate.of(2026, 10, 25));
    }

    // ---------- constructor / empty ----------

    public void testEmptyPantry() {
        assertEquals(0, pantry.size());
        assertTrue(pantry.getItems().isEmpty());
        assertFalse(pantry.remove("milk"));
        assertEquals(0, pantry.count("milk"));
    }

    // ---------- add ----------

    public void testAddNormal() {
        assertTrue(pantry.add(milkSoon));
        assertEquals(1, pantry.size());
        assertTrue(pantry.getItems().contains(milkSoon));
    }

    public void testAddNull() {
        try {
            pantry.add(null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertEquals(0, pantry.size());
        }
    }

    // ---------- remove ----------

    public void testRemoveOnlyCopy() {
        pantry.add(eggs);
        assertTrue(pantry.remove("eggs"));
        assertEquals(0, pantry.size());
        assertFalse(pantry.contains("eggs"));
    }

    public void testRemoveSoonestExpiringFirst() {
        pantry.add(milkLate);
        pantry.add(milkSoon);
        assertTrue(pantry.remove("milk"));
        assertEquals(1, pantry.size());
        assertEquals(milkLate, pantry.getItems().get(0));
    }

    public void testRemoveSameDateCopies() {
        pantry.add(milkSoon);
        pantry.add(new FoodItem("milk", LocalDate.of(2026, 10, 20)));
        assertTrue(pantry.remove("milk"));
        assertEquals(1, pantry.size());
    }

    public void testRemoveCleansName() {
        pantry.add(milkSoon);
        assertTrue(pantry.remove("  MILK "));
        assertEquals(0, pantry.size());
    }

    public void testRemoveMissing() {
        pantry.add(eggs);
        assertFalse(pantry.remove("butter"));
        assertEquals(1, pantry.size());
    }

    public void testRemoveBadName() {
        pantry.add(eggs);
        try {
            pantry.remove("   ");
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertEquals(1, pantry.size());
        }
    }

    // ---------- contains ----------

    public void testContainsNormal() {
        pantry.add(eggs);
        assertTrue(pantry.contains("eggs"));
        assertFalse(pantry.contains("milk"));
    }

    public void testContainsBadName() {
        try {
            pantry.contains(null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testContainsExpiredAndUnexpired() {
        LocalDate today = LocalDate.of(2026, 10, 21);
        pantry.add(new FoodItem("milk", today.minusDays(1)));
        assertTrue(pantry.containsExpired("milk", today));
        assertFalse(pantry.containsUnexpired("milk", today));

        pantry.add(new FoodItem("milk", today));
        assertTrue(pantry.containsUnexpired("milk", today));
    }

    public void testExpirationChecksRejectNullDate() {
        try {
            pantry.containsExpired("milk", null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
        try {
            pantry.containsUnexpired("milk", null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    // ---------- count ----------

    public void testCountAcrossDates() {
        pantry.add(milkSoon);
        pantry.add(milkLate);
        pantry.add(eggs);
        assertEquals(2, pantry.count("milk"));
        assertEquals(1, pantry.count("eggs"));
        assertEquals(0, pantry.count("butter"));
    }

    public void testCountBadName() {
        try {
            pantry.count("");
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    // ---------- size ----------

    public void testSizeCountsDuplicates() {
        pantry.add(milkSoon);
        pantry.add(milkSoon);
        assertEquals(2, pantry.size());
    }

    public void testSizeUnchangedByFailedAdd() {
        try {
            pantry.add(null);
        }
        catch (IllegalArgumentException e) {
            // expected
        }
        assertEquals(0, pantry.size());
    }

    // ---------- getItems ----------

    public void testGetItemsHasEverything() {
        pantry.add(milkSoon);
        pantry.add(eggs);
        List<FoodItem> list = pantry.getItems();
        assertEquals(2, list.size());
        assertTrue(list.contains(milkSoon));
        assertTrue(list.contains(eggs));
    }

    public void testGetItemsIsUnmodifiable() {
        pantry.add(eggs);
        try {
            pantry.getItems().add(milkSoon);
            fail("should have thrown");
        }
        catch (UnsupportedOperationException e) {
            assertEquals(1, pantry.size());
        }
    }
}
