/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.util.Collection;

/**
 *
 * @author david
 */
public class GraphFactory {
    
    public Graph createRandomTriangulation(Collection<Vertex> pointSet)
    {
        Graph graph = new Graph();
        /*
         * TODO: build random triangulation
         */
        return graph;
    }
    
    public Graph createDelaunayTriangulation(Collection<Vertex> pointSet)
    {
        Graph graph = new Graph();
        /*
         * TODO: build random graph
         */
        return graph;
    }
    
    /*
     * Creates a vertex with a random position (x, y) such that 0<=x<=1 and
     * 0<=y<=1.
     */
    public Vertex createRandomVertex()
    {
        /*
         * TODO: implement method
        */
        Vertex vertex = new Vertex();
        return vertex;
    }
}
