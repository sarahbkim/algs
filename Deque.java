package com.sarahkim;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

// REMOVE THIS FROM HOMEWORK

/**
 * Created by sarahbkim on 2/8/16.
 */
public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node head;
    private Node tail;

    public Deque() {
        size = 0;

        // sentinal nodes for head and tail
        head = new Node(null);
        tail = new Node(null);

        head.next = tail;
        head.prev = null;
        tail.prev = head;
        tail.next = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        validateItem(item);
        Node newItem = new Node(item);
        newItem.prev = head;
        newItem.next = head.next;
        head.next.prev = newItem;
        head.next = newItem;
        size++;
    }

    public void addLast(Item item) {
        validateItem(item);
        Node newItem = new Node(item);
        newItem.prev = tail.prev;
        newItem.next = tail;
        tail.prev.next = newItem;
        tail.prev = newItem;
        size++;
    }

    public Item removeFirst() {
        if (size > 0) {
            Node oldHead = head.next;
            // set new first item
            head.next = head.next.next;
            head.next.prev = head;

            // clean up old head pointers
            oldHead.next = null;
            oldHead.prev = null;

            size--;
            return oldHead.value;
        } else {
            throw new NoSuchElementException("Queue is empty");
        }
    }
    public Item removeLast() {
        if (size > 0) {
            Node oldTail = tail;
            // set new tail item
            tail.prev = tail.prev.prev;
            tail.prev.next = tail;

            // clean up old tail pointers
            oldTail.next = null;
            oldTail.prev = null;

            size--;
            return oldTail.value;
        } else {
            throw new NoSuchElementException("Queue is empty");
        }
    }
    public Iterator<Item> iterator() {

    }
    private void validateItem(Item item) throws NullPointerException {
         if (item == null) {
             throw new NullPointerException("Item cannot be null");
         }
    }

    // Node class
    private class Node {
        private Node next = null;
        private Node prev = null;
        private Item value;

        public Node(Item val) {
            value = val;
        }
    }

    public static void main (String[] args) {

    }
}
