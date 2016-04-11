package com.sarahkim.wordnet;
import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SAP {
    HashMap<String, CommonAncestor> pathLengths;
    Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new NullPointerException();
        }
        this.pathLengths = new HashMap<String, CommonAncestor>();
        this.G = G;
    }

    private void calculateAncestralPath(int v, int w) {
        BreadthFirstDirectedPaths bV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bW = new BreadthFirstDirectedPaths(G, w);

        int root = -1;
        double distance = Double.POSITIVE_INFINITY;

        for (int i=0; i<G.V(); i++) {
            if (bV.hasPathTo(i) && bW.hasPathTo(i)) {
                int currLength = bV.distTo(i)  + bW.distTo(i);

                if (currLength < distance) {
                    distance = currLength;
                    root = i;
                }
            }
        }
        pathLengths.put(key(v, w), new CommonAncestor(v, w, (int) distance, root));
    }

    private class CommonAncestor {
        public Integer x;
        public Integer y;
        public int length;
        public int ancestor;

        public CommonAncestor(int x, int y, int length, int ancestor) {
            this.x = x;
            this.y = y;
            this.length = length;
            this.ancestor = ancestor;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public void setAncestor(int ancestor) {
            this.ancestor = ancestor;
        }
        public int getAncestor() {
            return this.ancestor;
        }
        public int getLength() {
            return this.length;
        }
        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(this.x + ", " + this.y + "\n" + this.ancestor + ", " + this.length);
            return s.toString();
        }
    }

    private String key(int v, int w) {
       return v + "->" + w;
    }

    private CommonAncestor getCache(int v, int w) {
        return pathLengths.get(key(v, w)) != null ? pathLengths.get(key(v, w)) : pathLengths.get(key(w, v));
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        CommonAncestor cache = getCache(v, w);
        if (cache != null ){
            return cache.getLength();
        }
        calculateAncestralPath(v, w);
        return pathLengths.get(key(v, w)).getLength();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        CommonAncestor cache = getCache(v, w);
        if (cache != null ){
            return cache.getAncestor();
        }
        calculateAncestralPath(v, w);
        return pathLengths.get(key(v, w)).getAncestor();
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        return iterableCommonAncestorOrPath(v, w, "path");
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        return iterableCommonAncestorOrPath(v, w, "ancestor");
    }

    private int iterableCommonAncestorOrPath(Iterable<Integer> v, Iterable<Integer> w, String type) {
        int ancestor = -1;
        int shortestPath = -1;

        for (int vInt : v) {
            for (int wInt : w) {
                if (ancestor(vInt, wInt) != -1) {
                    int currPathLength = length(vInt, wInt);
                    if (shortestPath > currPathLength) {
                        shortestPath = currPathLength;
                        ancestor = ancestor(vInt, wInt);
                    }
                }
            }
        }
        if (type == "ancestor") {
            return ancestor;
        }
        // default -- return path length
        return shortestPath;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            StdOut.println(v + ", " + w);
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}


