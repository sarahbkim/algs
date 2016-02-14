package com.sarahkim;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdOut;
import junit.framework.TestCase;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by sarahbkim on 2/8/16.
 */
public class DequeTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private void attemptLoop(Deque d) {
        for (Object i : d) {
            StdOut.println(i);
        }
    }

    private Deque d;
    private int[] arr = {1, 2, 3};
    private int[] reversed = {3, 2, 1};

    @Before
    public void setUp() throws Exception {
        d = new Deque();
    }

    @Test
    public void testIsEmpty() throws Exception {
        d.addFirst(1);
        assertEquals(d.isEmpty(), false);
        d.removeLast();
        assertEquals(d.isEmpty(), true);
        d.addLast(3);
        assertEquals(d.isEmpty(), false);
        d.removeFirst();
        assertEquals(d.isEmpty(), true);
    }

    @Test
    public void testSize() throws Exception {
        d.addFirst(1);
        assertEquals(d.size(), 1);
        d.addLast(2);
        assertEquals(d.size(), 2);
        d.removeFirst();
        assertEquals(d.size(), 1);
        d.removeFirst();
        assertEquals(d.size(), 0);
    }

    @Test
    public void testAddFirst() throws Exception {
        for (Object i : arr) {
            d.addFirst(i);
        }
        assertEquals(d.size(), arr.length);
        int index = 0;
        for (Object i : d) {
            assertEquals(i, reversed[index]);
            index++;
        }
    }

    @Test
    public void testAddLast() throws Exception {
        for (Object i : arr) {
            d.addLast(i);
        }
        assertEquals(d.size(), arr.length);
        int index = 0;
        for (Object i : d) {
            assertEquals(i, arr[index]);
            index++;
        }
    }

    @Test
    public void testRemoveFirst() throws Exception {
        for (Object i : arr) {
            d.addFirst(i);
        }
        int index = arr.length;
        for (int i=0; i<arr.length;i++) {
            index--;
            Object removed = d.removeFirst();
            assertEquals(d.size(), index);
            assertEquals(removed, reversed[i]);
        }
        assertEquals(d.size(), 0);

        for (Object i : arr) {
            d.addLast(i);
        }
        int index2 = arr.length;
        for (int i=0; i<arr.length;i++) {
            index2--;
            Object removed = d.removeFirst();
            assertEquals(d.size(), index2);
            assertEquals(removed, arr[i]);

        }
        assertEquals(d.size(), 0);
    }

    @Test
    public void testRemoveLast() throws Exception {
        for (Object i : arr) {
            d.addLast(i);
        }
        int index = arr.length;
        for (int i=0; i<arr.length;i++) {
            index--;
            Object removed = d.removeLast();
            assertEquals(d.size(), index);
            assertEquals(removed, reversed[i]);
        }
        assertEquals(d.size(), 0);

        for (Object i : arr) {
            d.addFirst(i);
        }
        int index2 = arr.length;
        for (int i=0; i<arr.length;i++){
            index2--;
            Object removed = d.removeLast();
            assertEquals(d.size(), index2);
            assertEquals(removed, arr[i]);
        }
        assertEquals(d.size(), 0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveEmpty() throws Exception {
        d.removeFirst();
        d.removeLast();
    }

    @Test(expected = NullPointerException.class)
    public void testNullItem() throws Exception {
        d.addFirst(null);
        d.addLast(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIterator() throws Exception {
        assertEquals(d.size(), 0);
        Iterator i = d.iterator();
        i.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextIterator() throws Exception {
        assertEquals(d.size(), 0);
        Iterator i = d.iterator();
        i.next();
    }

}