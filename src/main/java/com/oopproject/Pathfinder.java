package com.oopproject;

import com.oopproject.world.locations.Location;
import com.oopproject.world.locations.Path;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Pathfinder {
    private HashMap<Pair<Integer, Integer>, Location> map_locations;
    public Pathfinder(HashMap<Pair<Integer, Integer>, Location> map_locations){
        this.map_locations = map_locations;
    }
    private int heuristic(int x1, int y1, int x2, int y2){
        // euclidean distance
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    private int cost(int x1, int y1, int x2, int y2){
        if (this.map_locations.containsKey(new Pair<>(x2, y2))){
            if (this.map_locations.get(new Pair<>(x2, y2)) instanceof Path){
                return 10;
            }
            else{
                return 10000;
            }
        }
        else{
            return 3000;
        }
    }
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
                if (t.x < 24){
                    queue.add(new Tuple(t.x + 1, t.y, t.cost + cost(t.x, t.y, t.x + 1, t.y), heuristic(t.x + 1, t.y, x2, y2), t));
                }
                if (t.y > 0){
                    queue.add(new Tuple(t.x, t.y - 1, t.cost + cost(t.x, t.y, t.x, t.y - 1), heuristic(t.x, t.y - 1, x2, y2), t));
                }
                if (t.y < 24){
                    queue.add(new Tuple(t.x, t.y + 1, t.cost + cost(t.x, t.y, t.x, t.y + 1), heuristic(t.x, t.y + 1, x2, y2), t));
                }
            }
        }
        return path;
    }
}
