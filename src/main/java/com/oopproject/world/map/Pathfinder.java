package com.oopproject.world.map;

import com.oopproject.world.map.locations.Location;
import com.oopproject.world.map.locations.Path;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Class that handles creating paths between locations. It uses A* algorithm to find the shortest path.
 * The Brotherhood of Steel has been using this algorithm for years and this is the reason why they are
 * so successful in their missions and mapping the wasteland.
 */
public class Pathfinder {
    private HashMap<Pair<Integer, Integer>, Location> locationHashMap;

    /**
     * Heuristic for the A* algorithm. It calculates the distance between two points using the euclidean distance.
     * @param x1 x coordinate of the first point
     * @param y1 y coordinate of the first point
     * @param x2 x coordinate of the second point
     * @param y2 y coordinate of the second point
     * @return the distance between the two points
     */
    private int heuristic(int x1, int y1, int x2, int y2){
        // manhattan distance
        return (int) Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * The cost function for the A* algorithm. It calculates the cost of a path. It rewards already existing path,
     * while punishing the creation of new paths and prohibits creating paths on top of hideouts and sources.
     * @param x1 x coordinate of the first point
     * @param y1 y coordinate of the first point
     * @param x2 x coordinate of the second point
     * @param y2 y coordinate of the second point
     * @return the cost of the path
     */
    private int cost(int x1, int y1, int x2, int y2){
        if (this.locationHashMap.containsKey(new Pair<>(x2, y2))){
            if (this.locationHashMap.get(new Pair<>(x2, y2)) instanceof Path){
                return 1;
            }
            else{
                return 10000;
            }
        }
        else{
            return 5000;
        }
    }

    /**
     * The A* algorithm. It finds the shortest path between two points.
     * @param x1 x coordinate of the first point
     * @param y1 y coordinate of the first point
     * @param x2 x coordinate of the second point
     * @param y2 y coordinate of the second point
     * @return the shortest path between the two points
     */
    public ArrayList<Pair<Integer, Integer>> findPath(int x1, int y1, int x2, int y2){
        //returns path which is comprised of pairs of coordinates
        ArrayList <Pair<Integer, Integer>> path = new ArrayList<>();
        // find path from p1 to p1, and make it look like manhattan distance
        // assign very high cost to already occupied cells by other things that path,
        // assign very low cost to these nodes
        // implementation:
        class Tuple implements Comparable<Tuple>{
            int x;
            int y;
            int cost;
            int heuristic;
            int total;
            Tuple parent;
            public Tuple(int x, int y, int cost, int heuristic, Tuple parent){
                this.x = x;
                this.y = y;
                this.cost = cost;
                this.heuristic = heuristic;
                this.total = cost + heuristic;
                this.parent = parent;
            }
            @Override
            public int compareTo(Tuple o) {
                return this.total - o.total;
            }
        }
        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        HashMap<Pair<Integer, Integer>, Tuple> visited = new HashMap<>();
        queue.add(new Tuple(x1, y1, 0, heuristic(x1, y1, x2, y2), null));
        while (!queue.isEmpty()){
            Tuple t = queue.poll();
            if (t.x == x2 && t.y == y2){
                //found path
                while (t.parent != null){
                    path.add(new Pair<>(t.x, t.y));
                    t = t.parent;
                }
                break;
            }
            if (!visited.containsKey(new Pair<>(t.x, t.y))){
                visited.put(new Pair<>(t.x, t.y), t);
                if (t.x > 0){
                    queue.add(new Tuple(t.x - 1, t.y, t.cost + cost(t.x, t.y, t.x - 1, t.y), heuristic(t.x - 1, t.y, x2, y2), t));
                }
                if (t.x < 23){
                    queue.add(new Tuple(t.x + 1, t.y, t.cost + cost(t.x, t.y, t.x + 1, t.y), heuristic(t.x + 1, t.y, x2, y2), t));
                }
                if (t.y > 0){
                    queue.add(new Tuple(t.x, t.y - 1, t.cost + cost(t.x, t.y, t.x, t.y - 1), heuristic(t.x, t.y - 1, x2, y2), t));
                }
                if (t.y < 23){
                    queue.add(new Tuple(t.x, t.y + 1, t.cost + cost(t.x, t.y, t.x, t.y + 1), heuristic(t.x, t.y + 1, x2, y2), t));
                }
            }
        }
        path.add(new Pair<>(x1, y1));
        // reverse the path
        ArrayList<Pair<Integer, Integer>> reversedPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--){
            reversedPath.add(path.get(i));
        }
        return reversedPath;
    }

    /**
     * Constructor for the Pathfinder class.
     * @param locationHashMap the hashmap of the locations
     */
    public Pathfinder(HashMap<Pair<Integer, Integer>, Location> locationHashMap){
        this.locationHashMap = locationHashMap;
    }
}
