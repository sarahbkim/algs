package com.sarahkim;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sarahbkim on 2/14/16.
 */
public class RandomizedQueueTest {

    private RandomizedQueue r;

    @Test
    public void testisEmpty() throws Exception {
        assertEquals(r.isEmpty(), true);
        r.enqueue(2);
        assertEquals(r.isEmpty(), false);
        r.dequeue();
        assertEquals(r.isEmpty(), true);
        int reps = 100;
        for(int i=0;i<reps;i++) {
            int t = StdRandom.uniform(0, 50);
            r.enqueue(t);
        }
        assertEquals(r.isEmpty(), false);
        for(int i=0;i<reps;i++) {
            r.dequeue();
        }
        assertEquals(r.isEmpty(), true);
    }
    @Test
    public void testSize() throws Exception {
        int reps = 100;
        for(int i=0;i<reps;i++) {
            int t = StdRandom.uniform(0, 50);
            r.enqueue(t);
        }
        assertEquals(r.size(), reps);
        for(int i=0;i<reps;i++) {
            r.dequeue();
        }
        assertEquals(r.size(), 0);
    }
    @Test(expected = NullPointerException.class)
    public void testNullQueue() throws Exception {
        r.enqueue(null);
    }
    @Test(expected = NoSuchElementException.class)
    public void testEmptyDequeue() throws Exception {
        r.dequeue();
    }
    @Test
    public void testSample() throws Exception {
        int reps = 100;
        for(int i=0;i<reps;i++) {
            int t = StdRandom.uniform(0, 50);
            r.enqueue(t);
        }
        for(int i=0;i<reps;i++) {
            Object sample = r.sample();
            assertNotNull(sample);
            assertEquals(r.size(), reps);
        }
    }
    @Test(expected = UnsupportedOperationException.class)
    public void testEmptyIterator() throws Exception {
        assertEquals(r.size(), 0);
        Iterator i = r.iterator();
        i.remove();
    }
    @Test(expected = NoSuchElementException.class)
    public void testNextIterator() throws Exception {
        assertEquals(r.size(), 0);
        Iterator i = r.iterator();
        i.next();
    }
    @Test
    public void testIterator() throws Exception {
        RandomizedQueue t = new RandomizedQueue();
        int reps = 10;
        for(int i=0;i<reps;i++) {
            r.enqueue(i);
        }
        assertEquals(r.size(), reps);

        Iterator i = r.iterator();
        Iterator j = t.iterator();
        assertEquals(i.hasNext(), true);
        assertEquals(j.hasNext(), false);
        for(int idx=0;idx<reps;idx++) {
            t.enqueue(idx);
        }

        assertEquals(j.hasNext(), true);

        for(int idx=0;idx<t.size();idx++){
            System.out.println(j.next());
        }
    }

    @Before
    public void setUp() throws Exception {
        r = new RandomizedQueue();
    }
    @Test
    public void testRandomnessOfIterators() throws Exception {
        RandomizedQueue t = new RandomizedQueue();
        int reps = 10;
        for(int i=0;i<reps;i++) {
            r.enqueue(i);
            t.enqueue(i);
        }

        Iterator rIt = r.iterator();
        Iterator tIt = t.iterator();
    }





}