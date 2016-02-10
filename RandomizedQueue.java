package com.sarahkim;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

/**
 * Created by sarahbkim on 2/10/16.
 */
// A randomized queue is similar to a stack or queue,
// except that the item removed is chosen uniformly at random from items in the data structure.


// The order of two or more iterators to the same randomized queue must be mutually independent;
// each iterator must maintain its own random order.
// Throw a java.lang.NullPointerException if the client attempts to add a null item;
// throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue;
// throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
// throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.

public class RandomizedQueue<Item> implements Iterable<Item> {
    public RandomizedQueue() {

    }
    public boolean isEmpty() {

    }
    public int size() {

    }
    public void enqueue(Item item) {

    }
    public Item dequeue() {

    }
    public Item sample() {

    }
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        public void remove () {
            throw new UnsupportedOperationException();
        }
        public boolean hasNext() {
        }
        public Item next() {
        }
    }
    public static void main(String[] args) {

    }
}