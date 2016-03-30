package smallworld;

import edu.princeton.cs.In;
import edu.princeton.cs.StdIn;
import edu.princeton.cs.StdOut;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/******************************************************************************
 *  Compilation:  javac PathFinder.java
 *  Execution:    java Pathfinder input.txt delimiter source
 *  Dependencies: Queue.java Stack.java Graph.java
 *  
 *  Runs breadth first search algorithm from source s on a graph G.
 *  After preprocessing the graph, can process shortest path queries
 *  from s to any vertex t.
 *
 *  % java PathFinder routes.txt " " JFK
 *  LAX
 *     JFK
 *     ORD
 *     PHX
 *     LAX
 *  distance 3
 *  MCO
 *     JFK
 *     MCO
 *  distance 1
 *  DFW
 *     JFK
 *     ORD
 *     DFW
 *  distance 2
 *
 ******************************************************************************/

public class PathFinderClient {

    // prev[v] = previous vertex on shortest path from s to v
    // dist[v] = length of shortest path from s to v
    private ST<String, String>  prev = new ST<String, String>();
    private ST<String, Integer> dist = new ST<String, Integer>();

    // run BFS in graph G from given source vertex s
    public PathFinderClient(Graph G, String s) {

        // put source on the queue
        Queue<String> q = new Queue<String>();
        q.enqueue(s);
        dist.put(s, 0);
        
        // repeated remove next vertex v from queue and insert
        // all its neighbors, provided they haven't yet been visited
        while (!q.isEmpty()) {
            String v = q.dequeue();
            for (String w : G.adjacentTo(v)) {
                if (!dist.contains(w)) {
                    q.enqueue(w);
                    dist.put(w, 1 + dist.get(v));
                    prev.put(w, v);
                }
            }
        }
    }

    // is v reachable from the source s?
    public boolean hasPathTo(String v) {
        return dist.contains(v);
    }

    // return the length of the shortest path from v to s
    public int distanceTo(String v) {
        if (!hasPathTo(v)) return Integer.MAX_VALUE;
        return dist.get(v);
    }

    // return the shortest path from v to s as an Iterable
    public Iterable<String> pathTo(String v) {
        Stack<String> path = new Stack<String>();
        while (v != null && dist.contains(v)) {
            path.push(v);
            v = prev.get(v);
        }
        return path;
    }

    public static List read(In in, String delimiter) {
        Graph G = new Graph();
        List<String> actors = new ArrayList<>();
        
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] names = line.split(delimiter);
            String movie = names[0];
            for (int i = 1; i < names.length; i++) {
                G.addEdge(movie, names[i]);
                actors.add(names[i]);
            }
        }
        return actors;
    }

    public static void main(String[] args) {
        String filename  = args[0];
        String delimiter = args[1];
        In in = new In(filename); 
        Graph G = GraphGenerator.read(in, delimiter);
        Iterable<String> actors = G.vertices();
        String s = "Bacon, Kevin";
       
        PathFinder pf = new PathFinder(G, s);
        int avg = 0;
        int notConnected = 0;
        for (String z : actors){
            int distance = pf.distanceTo(z)/2;
            if(distance < 10000 ){
            avg = avg + distance;
            }
            else{
                notConnected++;
            }
        }
        
        
        avg = avg/G.length();
        System.out.println("The average distance from " + s + "' is " + avg + "." );
        System.out.println("The number of people not connected with " + s + " is " + notConnected + ".");
        System.out.println();
        System.out.println();
        
        
        for (String z : actors){
            int distance = pf.distanceTo(z)/2;
            if (distance > avg  && distance < 1000 &&  !z.contains(".*[0-9].*")){
            StdOut.println(z + " is "+ distance + " degrees from " + s + ".");
            }
//            else{
//                StdOut.println(z + " is not connected to " + s + ".");
//            }
        }
//        while (!StdIn.isEmpty()) {
//            String t = StdIn.readLine();
//            for (String v : pf.pathTo(t)) {
//                StdOut.println("   " + v);
//            }
//            StdOut.println("distance " + pf.distanceTo(t)/2);
//        }

    
    }


}
