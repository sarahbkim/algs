package com.sarahkim;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

/**
 * Created by sarahbkim on 2/10/16.
 */
// A randomized queue is similar to a stack or queue,
// except that the item removed is chosen uniformly at random from items in the data structure.



public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int size;

    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
        size = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void enqueue(Item item) {
        if(item == null) throw new NullPointerException("You cannot add null item to queue");
        if (size == arr.length) adjustArraySize(2*arr.length);
        arr[size] = item;
        size++;
    }
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException("Queue is empty");
        // grab random item
        int rand = StdRandom.uniform(0, size);
        Item randomItem = arr[rand];

        // take care of holes in filled part of array
        if(rand != size) {
            arr[rand] = arr[size];
            arr[size] = null;
        }
        size--;

        if ((size > 0 ) && size == arr.length/4) adjustArraySize(arr.length/2);
        return randomItem;
    }
    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException("Queue is empty");
        int rand = StdRandom.uniform(0, size);
        return arr[rand];
    }
    private void adjustArraySize(int capacity) {
        assert capacity >= size;
        Item[] newArr = (Item[]) new Object[capacity];
        for(int i=0;i<size;i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }
    // The order of two or more iterators to the same randomized queue must be mutually independent;
    // each iterator must maintain its own random order.
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        int start = 0;
        int[] copied;
        boolean shuffled = false;

        public void remove () { throw new UnsupportedOperationException(); }
        // TODO: this must come out randomly too
        public boolean hasNext() {
            return start < size;
        }
        // TODO: this must come out randomly too
        public Item next() {
            if(!shuffled) {
                shuffleArray();
            }
            if(hasNext()) {
                int randomIdx = copied[start];
                start++;
                return arr[randomIdx];
            } else {
                throw new NoSuchElementException("No next element");
            }
        }
        private void shuffleArray() {
            copied = new int[size];
            for(int i=0;i<size;i++) {
                copied[i] = i;
            }
            StdRandom.shuffle(copied);
        }
    }

    public static void main(String[] args) {

    }
}