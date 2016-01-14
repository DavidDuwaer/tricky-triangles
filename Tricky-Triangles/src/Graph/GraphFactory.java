/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import GUI.GUI;
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

    Random rnd = new Random();
    
    public Graph createGraphWithOnlyPoints(Collection<Vertex> points) {
        Graph graph = new Graph();
        for (Vertex v : points) {
            graph.addVertex(v);
        }
        return graph;
    }

    public Graph createRandomGraphFromPoints(Collection<Vertex> points) {
        Graph graph = createGraphWithOnlyPoints(points);
        Random generator = new Random();
        int n = (int) Math.floor(generator.nextDouble() * points.size() * (points.size() - 1));
        int ii, jj;
        Vertex pointsArray[] = points.toArray(new Vertex[points.size()]);
        for (int i = 0; i < n; i++) {
            ii = generator.nextInt(points.size());
            jj = generator.nextInt(points.size());
            graph.addEdge(pointsArray[ii], pointsArray[jj]);
        }
        return graph;
    }

    public Graph createRandomTriangulation(Collection<Vertex> pointSet) {
        Graph graph = new Graph();
        graph = createDelaunayTriangulation(pointSet);
        Edge[] edges = graph.getEdges().toArray(new Edge[graph.getEdges().size()]);
        Random generator = new Random();
        for (int i = 0; i < pointSet.size() * 2; i++)
        {
            float r = generator.nextFloat();
            int ii = Math.round(r * (edges.length - 1));
            if (edges[ii].t.length == 2)
            {
                if(edges[ii].t[0] != null && edges[ii].t[1] != null){
                    graph.flipEdge(edges[ii]);
                }
            }
        }
        return graph;
    }

    public static void tick(){
        try{
            Thread.sleep(1000);
        } catch(Exception e){}
    }
    
    public Graph createDelaunayTriangulation(Collection<Vertex> pointSet) {
        
        Graph graph = new Graph();
        for (Vertex v : pointSet) {
            graph.addVertex(v);
        }

        // create triangle that contains all the points of pointSet
        Vertex bv1 = new Vertex(0, 1000, 200);
        Vertex bv2 = new Vertex(-1000, -1000, 201);
        Vertex bv3 = new Vertex(1000, -1000, 202);
        Edge bv1v2 = new Edge(bv1, bv2);
        Edge bv2v3 = new Edge(bv2, bv3);
        Edge bv3v1 = new Edge(bv3, bv1);
        Triangle tri = new Triangle(bv1, bv2, bv3, bv1v2, bv2v3, bv3v1);
        bv1v2.t[0] = tri;
        bv2v3.t[0] = tri;
        bv3v1.t[0] = tri;

        graph.addVertex(bv1);
        graph.addVertex(bv2);
        graph.addVertex(bv3);
        graph.addEdge(bv1v2);
        graph.addEdge(bv2v3);
        graph.addEdge(bv3v1);

        graph.addTriangle(tri);
        
        Triangle bigTriangle = null;
        // Add all the vertices one by one in the graph
        for (Vertex pr : pointSet) {
            System.out.println("Insert point " + pr.getID() + " (" + pr.getx() + ", "+ pr.gety() + ")");
            // Find triangle in D containing pr
            for (Triangle t : graph.getTriangle()) {
                bigTriangle = null;
                if (t.PointInTriangle(pr)) {
                    bigTriangle = t;
                    System.out.println(t.v[0].getID() + " " + t.v[1].getID() + " " + t.v[2].getID());
                    break;
                }
            }
            // make new edges
            if(bigTriangle== null){
                System.out.println("PANIEK! big triangle null");
            }
            Edge prv0 = new Edge(pr, bigTriangle.v[0]);
            Edge prv1 = new Edge(pr, bigTriangle.v[1]);
            Edge prv2 = new Edge(pr, bigTriangle.v[2]);

            // edges big Triangle
            Edge v0v1 = graph.getEdge(bigTriangle.v[0].getID(),bigTriangle.v[1].getID());
            Edge v1v2 = graph.getEdge(bigTriangle.v[1].getID(),bigTriangle.v[2].getID());
            Edge v0v2 = graph.getEdge(bigTriangle.v[0].getID(),bigTriangle.v[2].getID());

            //make new triangles
            Triangle prv0v1 = new Triangle(pr, bigTriangle.v[0], bigTriangle.v[1], prv0, prv1, v0v1);
            Triangle prv0v2 = new Triangle(pr, bigTriangle.v[0], bigTriangle.v[2], prv0, prv2, v0v2);
            Triangle prv2v1 = new Triangle(pr, bigTriangle.v[2], bigTriangle.v[1], prv2, prv1, v1v2);

            //update edges from big triangle
            for (Edge e : graph.getEdges())
            {
                for (int i = 0; i < 2; i++) {
                    if (e.t[i] == bigTriangle) {
                        if ((e.v[0] == bigTriangle.v[0] && e.v[1] == bigTriangle.v[1]) || (e.v[1] == bigTriangle.v[0] && e.v[0] == bigTriangle.v[1])) {
                            e.t[i] = prv0v1;
                            v0v1 = e;
                        } else if ((e.v[0] == bigTriangle.v[0] && e.v[1] == bigTriangle.v[2]) || (e.v[1] == bigTriangle.v[0] && e.v[0] == bigTriangle.v[2])) {
                            e.t[i] = prv0v2;
                            v0v2 = e;
                        } else if ((e.v[0] == bigTriangle.v[2] && e.v[1] == bigTriangle.v[1]) || (e.v[1] == bigTriangle.v[2] && e.v[0] == bigTriangle.v[1])) {
                            e.t[i] = prv2v1;
                            v1v2 = e;
                        }

                    }
                }
            }
            
            if(v0v1 == null || v0v2 == null || v1v2 == null){
                System.out.println("niet alle edges zijn gevonden");
            }

            //add triangles to edges
            prv0.t[0] = prv0v1;
            prv0.t[1] = prv0v2;
            prv1.t[0] = prv2v1;
            prv1.t[1] = prv0v1;
            prv2.t[0] = prv2v1;
            prv2.t[1] = prv0v2;

            // add edges to graph
            graph.addEdge(prv0);
            graph.addEdge(prv1);
            graph.addEdge(prv2);

            //remove triangle v1v2v3 from graph
            graph.removeTriangleSpec(bigTriangle);

            //add the new trinagles to the graph
            graph.addTriangle(prv0v1);
            graph.addTriangle(prv0v2);
            graph.addTriangle(prv2v1);

            //Legalize new edges
            if (v0v1 == null || v0v2 == null || v1v2 == null) {
                System.out.println("hier2");
            }
            graph.legalizeEdge(pr, v0v1);
            graph.legalizeEdge(pr, v0v2);
            graph.legalizeEdge(pr, v1v2);

        }
        //remove triangle (v1,v2,v3) and incident edges
        graph.removeVertex(bv1);
        graph.removeVertex(bv2);
        graph.removeVertex(bv3);

        return graph;
    }

    public Graph createConvexHull(Collection<Vertex> pointSet) {
        Graph graph = new Graph();

        for (Vertex v : pointSet) {
            graph.addVertex(v);
        }
        List<Vertex> P = new ArrayList<Vertex>();
        for (Vertex v : pointSet) {
            P.add(v);
        }

        int n = P.size(), k = 0;
        ArrayList<Vertex> H = new ArrayList<Vertex>();
        Collections.sort(P, new Comparator<Vertex>() {
            public int compare(Vertex s1, Vertex s2) {
                return s1.compareTo(s2);
            }
        });
        for (int i = 0; i < n; i++) {
            while (k >= 2 && Vertex.cross(H.get(k - 2), H.get(k - 1), P.get(i)) <= 0) {
                k--;
            }
            H.add(k, P.get(i));
            k++;
        }
        for (int i = n - 2, t = k + 1; i >= 0; i--) {
            while (k >= 2 && Vertex.cross(H.get(k - 2), H.get(k - 1), P.get(i)) <= 0) {
                k--;
            }
            H.add(k, P.get(i));
            k++;
        }
        while (H.size() > k) {
            H.remove(k);
        }
        for (int i = 1; i < H.size(); i++) {
            graph.addEdge(H.get(i - 1), H.get(i));
        }
        return graph;
    }

    /*
     * Creates a vertex with a random position (x, y) such that 0<=x<=1 and
     * 0<=y<=1.
     */
    public Vertex createRandomVertex(int id) {
        Random generator = new Random();
        Vertex vertex = new Vertex(
                generator.nextDouble(),
                generator.nextDouble(),
                id
        );
        return vertex;
    }

}
