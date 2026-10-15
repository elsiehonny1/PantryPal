/**
 * One link in a LinkedChain: holds an item and a reference to the next node.
 *
 * @param <T> type of item stored
 */
public class Node<T> {

    private T data;
    private Node<T> next;

    /** Creates a node with no next link. @throws IllegalArgumentException if data is null */
    public Node(T data) {
        this(data, null);
    }

    /** Creates a node linked to next. @throws IllegalArgumentException if data is null */
    public Node(T data, Node<T> next) {
        if (data == null) {
            throw new IllegalArgumentException("Node data cannot be null.");
        }
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    /** @throws IllegalArgumentException if data is null */
    public void setData(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Node data cannot be null.");
        }
        this.data = data;
    }

    /** null marks the end of the chain */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
