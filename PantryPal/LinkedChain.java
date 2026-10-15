import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A singly linked collection of items of one type. Duplicates are allowed,
 * null is not. New items go at the head.
 *
 * NOTE: if your class gave you its own LinkedChain.java, use that one instead
 * of this file. Pantry and MealPlan only call add, remove, size and iterator.
 *
 * @param <T> type of item stored
 */
public class LinkedChain<T> implements Iterable<T> {

    private Node<T> head;
    private int size;

    /** Creates an empty chain. */
    public LinkedChain() {
        head = null;
        size = 0;
    }

    /** Adds at the head. @throws IllegalArgumentException if item is null */
    public boolean add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("LinkedChain cannot hold null.");
        }
        head = new Node<>(item, head);
        size++;
        return true;
    }

    /** Removes one equal entry. Returns false for null, missing, or empty. */
    public boolean remove(T item) {
        if (item == null || head == null) {
            return false;
        }
        if (head.getData().equals(item)) {
            head = head.getNext();
            size--;
            return true;
        }
        Node<T> prev = head;
        Node<T> cur = head.getNext();
        while (cur != null) {
            if (cur.getData().equals(item)) {
                prev.setNext(cur.getNext());
                size--;
                return true;
            }
            prev = cur;
            cur = cur.getNext();
        }
        return false;
    }

    /** Returns true if an equal entry exists (false for null). */
    public boolean contains(T item) {
        return count(item) > 0;
    }

    /** Counts equal entries (0 for null or no match). */
    public int count(T item) {
        if (item == null) {
            return 0;
        }
        int total = 0;
        Node<T> cur = head;
        while (cur != null) {
            if (cur.getData().equals(item)) {
                total++;
            }
            cur = cur.getNext();
        }
        return total;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ChainIterator();
    }

    private class ChainIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            T data = current.getData();
            current = current.getNext();
            return data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
