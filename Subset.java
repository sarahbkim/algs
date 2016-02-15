//Subset client. Write a client program Subset.java that takes a
// command-line integer k; reads in a sequence of N strings from
// standard input using StdIn.readString(); and prints out exactly
// k of them, uniformly at random. Each item from the sequence can
// be printed out at most once. You may assume that 0 ≤ k ≤ N, where
// N is the number of string on standard input.
//        The running time of Subset must be linear in the s
// ize of the input. You may use only a constant amount of memory
// plus either one Deque or RandomizedQueue object of maximum size
// at most N, where N is the number of strings on standard input.
package com.sarahkim;
import edu.princeton.cs.algs4.StdIn;
import com.sarahkim.RandomizedQueue;

import java.util.Random;

/**
 * Created by sarahbkim on 2/14/16.
 */
public class Subset {


    public static void main(String[] args) {
        RandomizedQueue q = new RandomizedQueue();
        if(args.length > 0) {
            int items = Integer.parseInt(args[0]);
            while (!StdIn.isEmpty()) {
                String str = StdIn.readString();
                q.enqueue(str);
            }
            for (int i = 0; i<items; i++) {
                System.out.println(q.dequeue());
            }
        }
    }
}
