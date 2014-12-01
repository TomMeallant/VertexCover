package fr.vertex.service;

import org.junit.Before;
import org.junit.Test;

import fr.vertex.domain.Vertex;

import static junit.framework.Assert.assertEquals;


import static junit.framework.Assert.assertEquals;

/**
 * Created by Tom on 29/11/2014.
 */
public class GraphTest {
    private Vertex lille = new Vertex("Lille");
    private Vertex paris = new Vertex("Paris");
    private Vertex reims = new Vertex("Reims");
    private Vertex nancy = new Vertex("Nancy");
    private Vertex lyon = new Vertex("Lyon");
    private Vertex marseille = new Vertex("Marseille");
    private Vertex lemans = new Vertex("Le Mans");
    private Vertex nantes = new Vertex("Nantes");
    private Vertex bordeaux = new Vertex("Bordeaux");
    private Vertex toulouse = new Vertex("Toulouse");
    private Vertex clermont = new Vertex("Clermont Ferrant");
    private Vertex montpellier = new Vertex("Montpellier");

    @Before
    public void setup() {
        lille.connectTo(reims, 206);
        lille.connectTo(paris, 222);
        lille.connectTo(nancy, 418);

        reims.connectTo(paris, 144);
        reims.connectTo(nancy, 245);
        reims.connectTo(lyon, 489);

        paris.connectTo(lyon, 465);
        paris.connectTo(lemans, 208);
        paris.connectTo(clermont, 423);

        lyon.connectTo(clermont, 166);
        lyon.connectTo(marseille, 313);
        lyon.connectTo(montpellier, 304);

        lemans.connectTo(nantes, 189);
        lemans.connectTo(bordeaux, 443);

        nantes.connectTo(bordeaux, 347);

        bordeaux.connectTo(toulouse, 243);

        toulouse.connectTo(montpellier, 245);

        montpellier.connectTo(marseille, 169);
        montpellier.connectTo(toulouse, 245);

        marseille.connectTo(montpellier, 169);

        clermont.connectTo(lyon, 166);
        clermont.connectTo(montpellier, 333);
        clermont.connectTo(marseille, 474);
    }

    @Test
    public void getDistanceForTwoAdjacentVertices() {
        Graph graph = new Graph(paris, lyon);

        assertEquals(graph.getDistance("Paris", "Lyon"), 465);
    }
    @Test
    public void getDistanceForTwoNonAdjacentVertices() {
        Graph graph = new Graph(lille, paris, lemans);

        assertEquals(graph.getDistance("Lille", "Le Mans"), 430);
    }
    @Test
    public void getDistanceForLongWay(){
        Graph graph = new Graph(lille, paris, lemans, nantes);

        assertEquals(graph.getDistance("Lille", "Nantes"), 619);
    }
    @Test
    public void getDistanceWithoutCycles(){
        Graph graph = new Graph(clermont,lyon,montpellier,toulouse);

        assertEquals(graph.getDistance("Clermont Ferrant", "Toulouse"),578);
    }
    @Test
    public void getDistanceWithNullGraph(){
        Graph graph = new Graph();

        assertEquals(graph.getDistance("Clermont Ferrant","Toulouse"),0);
    }
    @Test
    public void getDistanceWithoutWays(){
        Graph graph = new Graph(clermont,montpellier);

        assertEquals(graph.getDistance("Lille","Nantes"),0);
    }
    @Test
    public  void getDistanceToUnknownCity() {
        Graph graph = new Graph(clermont, montpellier);

        assertEquals(graph.getDistance("Lille", "Nante"), 0);
    }
    @Test
    public void getDistanceToTheSameCity(){
        Graph graph = new Graph(clermont,lyon,montpellier,toulouse);

        assertEquals(graph.getDistance("Clermont","Clermont"),0);
    }
    @Test
    public void getDistanceThroughAlltheGraph(){
        Graph graph =new Graph(lille, reims, paris, clermont, lyon, marseille, montpellier, nantes, nancy, lemans, bordeaux, toulouse);

        assertEquals(graph.getDistance("Nantes","Marseille"),1004);
    }
    @Test
    public void getDistanceThroughAlltheGraphWithoutWays() {
        Graph graph = new Graph(lille, reims, paris, clermont, lyon, marseille, montpellier, nantes, nancy, lemans, bordeaux, toulouse);

        assertEquals(graph.getDistance("Nantes", "Lyon"), 0);
    }
}