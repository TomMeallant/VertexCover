package fr.vertex.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 29/11/2014.
 */
public class Vertex {
    private String name;

    private List<Edge> edges = new ArrayList<Edge>();

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void connectTo(Vertex target, int distance) {
        edges.add(new Edge(target, distance));
    }
    public List<Edge> getEdges(){
        return edges;
    }
    public boolean isconnectTo(String target) {
        for (Edge edge : this.getEdges()) {
            // Regarde si un chemin est direct jusqu'au point d'arrivée
            if (edge.getTarget().getName().equals(target))
                return true;
        }
        return false;
    }
    public int distanceToTarget(String target){
        for (Edge edge : this.getEdges()) {
            // Regarde si un chemin est direct jusqu'au point d'arrivée
            if (edge.getTarget().getName().equals(target))
                return edge.getDistance();
        }
        return 0;
    }
}
