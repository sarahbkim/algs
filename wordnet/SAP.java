package com.sarahkim.wordnet;
import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SAP {
    HashMap<Integer, ArrayList<Integer>> ancestors;
    HashMap<String, CommonAncestor> pathLengths;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new NullPointerException();
        }
        ancestors = new HashMap<Integer, ArrayList<Integer>>();
        pathLengths = new HashMap<String, CommonAncestor>();

        for (int i=0; i<G.V(); i++) {
            BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(G, i);
            for (int j=0; j<G.V(); j++) {
                if (b.pathTo(j) != null)  {
                    for (int path: b.pathTo(j)) {
                        if (ancestors.get(i) == null) ancestors.put(i, new ArrayList<Integer>());
                        if (!ancestors.get(i).contains(path)) ancestors.get(i).add(path);
                    }
                }
            }
        }
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

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        CommonAncestor cache = pathLengths.get(key(v, w)) != null ? pathLengths.get(key(v, w)) : pathLengths.get(key(w, v));
        if (cache != null ){
            return cache.getLength();
        }
        calculateAncestralPath(v, w);
        return pathLengths.get(key(v, w)).getLength();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        CommonAncestor cache = pathLengths.get(key(v, w)) != null ? pathLengths.get(key(v, w)) : pathLengths.get(key(w, v));
        if (cache != null ){
            return cache.getAncestor();
        }
        calculateAncestralPath(v, w);
        return pathLengths.get(key(v, w)).getAncestor();
    }

    private void calculateAncestralPath(int v, int w) {
        if (ancestors.get(v) != null && ancestors.get(w) != null) {
            ArrayList<Integer> longer = ancestors.get(v).size() > ancestors.get(w).size() ? ancestors.get(v) : ancestors.get(w);
            ArrayList<Integer> other = longer.equals(ancestors.get(v)) ? ancestors.get(w) : ancestors.get(v);
            for (int i=0; i<longer.size(); i++) {
                if (other.contains(longer.get(i))) {
                    int ancestor = longer.get(i);
                    int length = longer.indexOf(ancestor) + other.indexOf(ancestor);
                    CommonAncestor c = new CommonAncestor(v, w, length, ancestor);
                    pathLengths.put(key(v, w), c);
                    break;
                }
            }
        }

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


