import java.time.LocalDate;
import java.util.Arrays;

/** Tests for Validator. Dates use a fixed "today" so they never go stale. */
public class ValidatorTest extends student.TestCase {

    private LocalDate today;

    public void setUp() {
        today = LocalDate.of(2026, 10, 6);
    }

    private void assertBadName(String raw) {
        try {
            Validator.cleanName(raw);
            fail("should have thrown for: " + raw);
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    private void assertBadDate(String raw) {
        try {
            Validator.parseDate(raw);
            fail("should have thrown for: " + raw);
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    // ---- cleanName ----

    public void testCleanNameNormal() {
        assertEquals("milk", Validator.cleanName(" Milk "));
        assertEquals("milk", Validator.cleanName("milk"));
    }

    public void testCleanNameBad() {
        assertBadName(null);
        assertBadName("");
        assertBadName("   ");
        assertBadName("!!!");
        assertBadName("\uD83D\uDE00");
    }

    public void testCleanNameLength() {
        String fifty = "a".repeat(50);
        assertEquals(fifty, Validator.cleanName(fifty));
        assertBadName("a".repeat(51));
    }

    // ---- parseDate ----

    public void testParseDateGood() {
        assertEquals(LocalDate.of(2026, 10, 20), Validator.parseDate("10/20/2026"));
    }

    public void testParseDateWrongFormat() {
        assertBadDate("2026-10-20");
        assertBadDate("10/20/26");
        assertBadDate("abc");
        assertBadDate("");
        assertBadDate(null);
    }

    public void testParseDateImpossible() {
        assertBadDate("13/01/2026");
        assertBadDate("02/30/2026");
    }

    public void testParseDateLeapYears() {
        assertEquals(LocalDate.of(2028, 2, 29), Validator.parseDate("02/29/2028"));
        assertBadDate("02/29/2027");
    }

    // ---- isPast ----

    public void testIsPast() {
        assertTrue(Validator.isPast(LocalDate.of(2026, 10, 5), today));
        assertFalse(Validator.isPast(LocalDate.of(2026, 10, 7), today));
    }

    public void testIsPastToday() {
        assertFalse(Validator.isPast(today, today));
    }

    public void testIsPastNull() {
        try {
            Validator.isPast(null, today);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
        try {
            Validator.isPast(today, null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    // ---- parseIngredients ----

    public void testParseIngredients() {
        assertEquals(Arrays.asList("eggs", "milk", "flour"),
            Validator.parseIngredients("eggs, milk ,flour"));
        assertEquals(Arrays.asList("eggs", "milk"), Validator.parseIngredients("eggs,,milk"));
        assertEquals(Arrays.asList("eggs"), Validator.parseIngredients("eggs"));
    }

    public void testParseIngredientsBad() {
        String[] bad = {null, "", " , ,", "eggs,!!!"};
        for (String raw : bad) {
            try {
                Validator.parseIngredients(raw);
                fail("should have thrown for: " + raw);
            }
            catch (IllegalArgumentException e) {
                assertNotNull(e);
            }
        }
    }
}
