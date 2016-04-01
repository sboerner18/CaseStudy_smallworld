package smallworld;

import edu.princeton.cs.In;
import edu.princeton.cs.StdDraw;
import edu.princeton.cs.StdIn;
import edu.princeton.cs.StdOut;
import java.awt.Color;
import static java.lang.Math.round;
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

public class PathFinderTotals {

    // prev[v] = previous vertex on shortest path from s to v
    // dist[v] = length of shortest path from s to v
    private ST<String, String>  prev = new ST<String, String>();
    private ST<String, Integer> dist = new ST<String, Integer>();

    // run BFS in graph G from given source vertex s
    public PathFinderTotals(Graph G, String s) {

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
        String s = args[2];
       
        PathFinder pf = new PathFinder(G, s);
//        int avg = 0;
//        int notConnected = 0;
//        for (String z : actors){
//            int distance = pf.distanceTo(z)/2;
//            if(distance < 10000 ){
//            avg = avg + distance;
//            }
//            else{
//                notConnected++;
//            }
//        }
        
        
//        avg = avg/G.length();
//        System.out.println("The average distance from " + s + "' is " + avg + "." );
//        System.out.println("The number of people not connected with " + s + " is " + notConnected + ".");
//        System.out.println();
//        System.out.println();
        
        
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        int none = 0;
        
        for (String z : actors){
            int distance = pf.distanceTo(z)/2;
            if (distance == 1 &&  !z.contains(".*[0-9].*")){
            one++;
            }
            else if (distance == 2 &&   !z.contains(".*[0-9].*")){
                two++;
            }
            else if (distance == 3 &&   !z.contains(".*[0-9].*")){
                three++;
            }
            else if (distance == 4 &&   !z.contains(".*[0-9].*")){
                four++;
            }
            else if (distance == 5 &&   !z.contains(".*[0-9].*")){
                five++;
            }
            else if (distance == 6 &&   !z.contains(".*[0-9].*")){
                six++;
            }
            else {
                none++;
            }
            
//            else{
//                StdOut.println(z + " is not connected to " + s + ".");
//            }
        } // for()
        
        int max = 0;
            if (none > max){
                max =  none;
            }
            if(one > max){
                max = one;
            }
            if(two > max){
                max = two;
            }
            if(three > max){
                max = three;
            }
            if(four > max){
                max = four;
            }
            if(five > max){
                max = five;
            }
            if(six > max){
                max = six;
            }
            System.out.println("The max is " + max + ".");
            max =  max + (int) Math.round(max * 0.1);
        
         System.out.println("0: " + none + " 1: " + one + " 2: " + two + " 3: " + three + " 4: " + four + " 5: " + five + "  6: " + six);
         
         StdDraw.setCanvasSize(1280, 720);
         StdDraw.setXscale();
        StdDraw.setYscale();
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenRadius(.03);
        
        
        
         StdDraw.line( .142, 0 , .142, (double) none/max);
         StdDraw.text(.142, -.04, "Zero: " + none);
         
         StdDraw.line(.285, 0, .285, (double) one/max);
         StdDraw.text(.285, -.04, "One: " + one);
         
         StdDraw.line(.428, 0, .428, (double) two/max);
         StdDraw.text(.428, -.04, "Two: " + two);
         
         StdDraw.line(.571 , 0, .571, (double) three/max);
         StdDraw.text(.571, -.04, "Three: " + three);
         
         StdDraw.line(.714, 0, .714, (double) four/max);
         StdDraw.text(.714, -.04, "Four: " + four);
         
         StdDraw.line(.857, 0, .857, (double) five/max);
         StdDraw.text(.857, -.04, "Five: " + five);
         
//         StdDraw.line(1, 0, 1, (double) six/max);
//         StdDraw.text(1 , -.04, "Zero: " + six);
         
         
//         double ycord = 0;
//         for( int x = 0; x < none; x+= 3){
//             StdDraw.point( 0.1 , ycord);
//             ycord += .00001;
//         }
//         ycord = 0;
//         for( int x = 0; x < one; x+= 3){
//             StdDraw.point( 0.2 , ycord);
//             ycord += .00001;
//         }
//          ycord = 0;
//         for( int x = 0; x < two; x+= 3){
//             StdDraw.point( 0.3 , ycord);
//             ycord += .00001;
//         }
//          ycord = 0;
//         for( int x = 0; x < three; x+= 3){
//             StdDraw.point( 0.4 , ycord);
//             ycord += .00001;
//         }
//          ycord = 0;
//         for( int x = 0; x < four; x+= 3){
//             StdDraw.point( 0.5 , ycord);
//             ycord += .00001;
//         }
//          ycord = 0;
//         for( int x = 0; x < five; x+= 3){
//             StdDraw.point( 0.6 , ycord);
//             ycord += .00001;
//         }
         
         
//        while (!StdIn.isEmpty()) {
//            String t = StdIn.readLine();
//            for (String v : pf.pathTo(t)) {
//                StdOut.println("   " + v);
//            }
//            StdOut.println("distance " + pf.distanceTo(t)/2);
//        }

    
    }


}
