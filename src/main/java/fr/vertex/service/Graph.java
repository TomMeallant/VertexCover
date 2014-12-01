package fr.vertex.service;

import fr.vertex.domain.Edge;
import fr.vertex.domain.Vertex;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;

/**
 * Created by Tom on 29/11/2014.
 */
public class Graph {
    private List<Vertex> vertices = new ArrayList<Vertex>();

    public Graph(Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    //fonction récursive qui fait évoluer la distance en parcourant tous les chemins possibles et retenant la distance optimale
    public int parcours (Vertex noeud, String to, List<Vertex> dejatraverses, int distopt, int distanceparcourue) {
        if (dejatraverses.size() == vertices.size()) {
            return distopt;
        } else {
            for (Edge edge : noeud.getEdges()) {
                Vertex target = edge.getTarget();
                if (target.getName().equals(to)) {
                    if (distopt ==0){
                        distopt= distanceparcourue + edge.getDistance();
                    }
                    else {
                        distopt = min(distopt, distanceparcourue + edge.getDistance());
                    }
                } else {
                    if (vertices.contains(target) && !dejatraverses.contains(target)) {
                        dejatraverses.add(target);
                        distopt=parcours(target, to, dejatraverses, distopt, distanceparcourue+ edge.getDistance());
                    }
                }
            }
            return distopt;
        }
    }
    public int getDistance(String from, String to){
        if (vertices==null){
            return 0;
        }
        else {
            for (Vertex noeud : vertices) {
                //Choisit le noeud de départ
                if (noeud.getName().equals(from)) {
                    //si le noeud est directement connecté
                    if (noeud.isconnectTo(to)) {
                        return noeud.distanceToTarget(to);
                    } else {
                        int distance = 0;
                        int distanceparcourue = 0;
                        for (Edge edge : noeud.getEdges()) {
                            Vertex target = edge.getTarget();
                            if (vertices.contains(target)) {
                                //regarde si le noeud est indirectement connecté
                                if (target.isconnectTo(to)) {
                                    if (distance == 0) {
                                        distance = target.distanceToTarget(to) + edge.getDistance();
                                    } else {
                                        distance = min(distance, target.distanceToTarget(to) + edge.getDistance());
                                    }
                                } else {
                                    List<Vertex> dejatraverses = new ArrayList<Vertex>();
                                    dejatraverses.add(noeud);
                                    dejatraverses.add(target);
                                    distanceparcourue = edge.getDistance();
                                    //on appelle la fonction récursive et enregistre la valeur de distance si elle est
                                    //meilleure que la précédente. 0 correspondant à pas de réponses.
                                    if (distance == 0) {
                                        distance = parcours(target, to, dejatraverses, 0, distanceparcourue);
                                    } else {
                                        distance = min(distance, parcours(target, to, dejatraverses, 0, distanceparcourue));
                                    }

                                }
                            }
                        }
                        return distance;
                    }
                }
            }
            return 0;
        }
    }
}
