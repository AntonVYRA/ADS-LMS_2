package by.it.group351051.вырко.lesson11;

import java.util.Set;
import java.util.Iterator;

public class MyHashSet<E> implements Set<E> {
    private static final int DEFAULT_CAPACITY = 16;

    private Node<E>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        buckets = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private static class Node<E> {
        E value;
        Node<E> next;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private int getIndex(Object o) {
        int hash = o == null ? 0 : o.hashCode();
        return (hash & 0x7FFFFFFF) % buckets.length;
    }

    @Override
    public boolean add(Object o) {
        E element = (E) o;
        int index = getIndex(element);
        Node<E> current = buckets[index];

        while (current != null) {
            if (current.value == null && element == null ||
                    current.value != null && current.value.equals(element)) {
                return false; // Уже есть
            }
            current = current.next;
        }

        buckets[index] = new Node<>(element, buckets[index]);
        size++;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o);
        Node<E> current = buckets[index];

        while (current != null) {
            if (current.value == null && o == null ||
                    current.value != null && current.value.equals(o)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);
        Node<E> current = buckets[index];
        Node<E> prev = null;

        while (current != null) {
            if (current.value == null && o == null ||
                    current.value != null && current.value.equals(o)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Просто для красивого вывода — порядок не гарантирован
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;

        for (Node<E> bucket : buckets) {
            Node<E> current = bucket;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.value);
                first = false;
                current = current.next;
            }
        }

        sb.append("]");
        return sb.toString();
    }

    // Остальные методы Set<E> можно реализовать при необходимости
    // Но по заданию они не требуются:
    @Override public Iterator<E> iterator() { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(java.util.Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
}
