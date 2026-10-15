import java.time.LocalDate;

/** Tests for FoodItem. */
public class FoodItemTest extends student.TestCase {

    private FoodItem milk;
    private LocalDate date;

    public void setUp() {
        date = LocalDate.of(2026, 10, 20);
        milk = new FoodItem("  Milk ", date);
    }

    public void testConstructor() {
        assertEquals("milk", milk.getName());
        assertEquals(date, milk.getExpirationDate());
    }

    public void testPastDateAllowed() {
        FoodItem old = new FoodItem("bread", LocalDate.of(2000, 1, 1));
        assertEquals(LocalDate.of(2000, 1, 1), old.getExpirationDate());
    }

    public void testBadName() {
        try {
            new FoodItem("   ", date);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testNullDate() {
        try {
            new FoodItem("milk", null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testEquals() {
        assertTrue(milk.equals(new FoodItem("milk", date)));
        assertFalse(milk.equals(new FoodItem("eggs", date)));
        assertFalse(milk.equals(new FoodItem("milk", date.plusDays(1))));
        assertTrue(milk.equals(milk));
        assertFalse(milk.equals(null));
        assertFalse(milk.equals("milk"));
    }

    public void testHashCode() {
        assertEquals(milk.hashCode(), new FoodItem("MILK", date).hashCode());
    }

    public void testToString() {
        assertEquals("milk (expires 10/20/2026)", milk.toString());
    }
}
