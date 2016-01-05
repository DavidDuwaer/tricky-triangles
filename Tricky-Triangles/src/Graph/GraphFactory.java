/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.*;

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
    
    public Graph createDelaunayTriangulation(Collection<Vertex> vertexSet)
    {
        Graph graph = new Graph();
        /*
         * TODO: build random triangulation
         */
        return graph;
    }
    
    public Graph createConvexHull(Collection<Vertex> pointSet)
    {
        Graph graph = new Graph();
        
        for(Vertex v : pointSet){
            graph.addVertex(v);
        }
        List<Vertex> P = new ArrayList<Vertex>();
        for(Vertex v: pointSet){
            P.add(v);
        }
        
        int n = P.size(), k=0;
        ArrayList<Vertex> H = new ArrayList<Vertex>();
        Collections.sort(P,new Comparator<Vertex>(){
                     public int compare(Vertex s1,Vertex s2){
                             return s1.compareTo(s2);
                     }});
        for (int i = 0; i<n; i++){
            while (k>=2 && Vertex.cross(H.get(k-2), H.get(k-1), P.get(i))<=0){
                k--;
            }
            H.add(k, P.get(i));
            k++;
        }
        for (int i = n-2, t=k+1; i>=0; i--){
            while (k>=2 && Vertex.cross(H.get(k-2), H.get(k-1), P.get(i))<=0){
                k--;
            }
            H.add(k, P.get(i));
            k++;
        }
        while (H.size() >k){
            H.remove(k);
        }
        for(int i = 1; i < H.size() ;i++){
            graph.addEdge(H.get(i-1), H.get(i));
        }
        return graph;
    }
    
    /*
     * Creates a vertex with a random position (x, y) such that 0<=x<=1 and
     * 0<=y<=1.
     */
    public Vertex createRandomVertex(int id)
    {
        Random generator = new Random();
        Vertex vertex = new Vertex(
                generator.nextDouble(),
                generator.nextDouble(),
                id
        );
        return vertex;
    }
    
}
