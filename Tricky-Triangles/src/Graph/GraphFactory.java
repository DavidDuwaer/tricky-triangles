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

    public Graph createRandomTriangulation(Collection<Vertex> vertexSet) {
        Graph graph = new Graph();
        /*
         * TODO: build random triangulation
         */
        return graph;
    }

    public Graph createDelaunayTriangulation(Collection<Vertex> pointSet) {
        Graph graph = new Graph();
        for (Vertex v : pointSet) {
            graph.addVertex(v);
            System.out.println(v.getx());
        }

        Vertex bv1 = new Vertex(0, Integer.MAX_VALUE, -1);
        Vertex bv2 = new Vertex(Integer.MIN_VALUE, Integer.MIN_VALUE, -1);
        Vertex bv3 = new Vertex(Integer.MAX_VALUE, Integer.MIN_VALUE, -1);

        Edge bv1v2 = new Edge(bv1, bv2);
        Edge bv2v3 = new Edge(bv2, bv3);
        Edge bv3v1 = new Edge(bv3, bv1);

        Triangle tri = new Triangle(bv1,bv2,bv3);

        bv1v2.t[0] = tri;
        bv2v3.t[0] = tri;
        bv3v1.t[0] = tri;

        graph.addEdge(bv1v2);
        graph.addEdge(bv2v3);
        graph.addEdge(bv3v1);

        graph.addTriangle(tri);
        Triangle bigTriangle = null;
        // Add all the vertices one by one in the graph
        for (Vertex pr : pointSet) {
            // Find triangle in D containing pr
            for (Triangle t : graph.getTriangle()) {
                bigTriangle = null;
                if (t.PointInTriangle(pr)) {
                    bigTriangle = t;
                    break;
                }
            }
            // make new edges
            Edge prv0 = new Edge(pr, bigTriangle.v[0]);
            Edge prv1 = new Edge(pr, bigTriangle.v[1]);
            Edge prv2 = new Edge(pr, bigTriangle.v[2]);

            // edges big Triangle
            Edge v0v1 = null;
            Edge v1v2 = null;
            Edge v2v0 = null;

            //make new triangles
            Triangle prv0v1 = new Triangle(pr, bigTriangle.v[0], bigTriangle.v[1]);
            Triangle prv0v2 = new Triangle(pr, bigTriangle.v[0], bigTriangle.v[2]);
            Triangle prv2v1 = new Triangle(pr, bigTriangle.v[2], bigTriangle.v[1]);

            //update edges from big triangle
            for (Edge e : graph.getEdges()) {
                for (int i = 0; i < 2; i++) {
                    if (e.t[i] == bigTriangle) {
                        if ((e.v[0] == bigTriangle.v[0] && e.v[1] == bigTriangle.v[1]) || (e.v[1] == bigTriangle.v[0] && e.v[0] == bigTriangle.v[1])) {
                            e.t[i] = prv0v1;
                            v0v1 = e;
                        } else if ((e.v[0] == bigTriangle.v[0] && e.v[1] == bigTriangle.v[2]) || (e.v[1] == bigTriangle.v[0] && e.v[0] == bigTriangle.v[2])) {
                            e.t[i] = prv0v2;
                            v2v0 = e;
                        } else if ((e.v[0] == bigTriangle.v[2] && e.v[1] == bigTriangle.v[1]) || (e.v[1] == bigTriangle.v[2] && e.v[0] == bigTriangle.v[1])) {
                            e.t[i] = prv2v1;
                            v1v2 = e;
                        }

                    }
                }
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
            graph.legalizeEdge(pr, v0v1);
            graph.legalizeEdge(pr, v2v0);
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
