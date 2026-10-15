/** Tests for Node. */
public class NodeTest extends student.TestCase {

    private Node<String> node;

    public void setUp() {
        node = new Node<>("milk");
    }

    public void testConstructorNoNext() {
        assertEquals("milk", node.getData());
        assertNull(node.getNext());
    }

    public void testConstructorWithNext() {
        Node<String> second = new Node<>("eggs", node);
        assertEquals("eggs", second.getData());
        assertSame(node, second.getNext());
    }

    public void testConstructorNullData() {
        try {
            new Node<String>(null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
        try {
            new Node<String>(null, node);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testSetData() {
        node.setData("eggs");
        assertEquals("eggs", node.getData());
    }

    public void testSetDataNull() {
        try {
            node.setData(null);
            fail("should have thrown");
        }
        catch (IllegalArgumentException e) {
            assertEquals("milk", node.getData());
        }
    }

    public void testSetNext() {
        Node<String> other = new Node<>("eggs");
        node.setNext(other);
        assertSame(other, node.getNext());
        node.setNext(null);
        assertNull(node.getNext());
    }
}
