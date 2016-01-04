/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 *
 * @author david
 */
public class GraphFactory {
    
    public Graph createGraphWithOnlyPoints(Collection<Vertex> points)
    {
        Graph graph = new Graph();
        for (Vertex v : points)
        {
            graph.addVertex(v);
        }
        return graph;
    }
    
    public Graph createRandomGraphFromPoints(Collection<Vertex> points)
    {
        Graph graph = createGraphWithOnlyPoints(points);
        Random generator = new Random();
        int n = (int) Math.floor(generator.nextDouble() * points.size() * (points.size() - 1));
        int ii, jj;
        Vertex pointsArray[] = points.toArray(new Vertex[points.size()]);
        for (int i = 0; i < n; i++)
        {
            ii = generator.nextInt(points.size());
            jj = generator.nextInt(points.size());
            graph.addEdge(pointsArray[ii], pointsArray[jj]);
        }
        return graph;
    }
    
    public Graph createRandomTriangulation(Collection<Vertex> vertexSet)
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
        Random generator = new Random();
        Vertex vertex = new Vertex(
                generator.nextDouble(),
                generator.nextDouble()
        );
        return vertex;
    }
}
