package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Queue;

/**
 *
 * @author s115869
 */
public class Graph {

    private Set<Vertex> v;
    private Set<Edge> e;
    private Set<Triangle> t;

    public void flipEdge(Edge e)
    {
        Triangle[] t = e.t;

        // vertices of triangle 0
        ArrayList<Vertex> vertices1 = new ArrayList<>();
        vertices1.addAll(Arrays.asList(t[0].v));

        // vertices of triangle 1
        ArrayList<Vertex> vertices2 = new ArrayList<>();
        vertices2.addAll(Arrays.asList(t[1].v));

        // vertices that the triangles have in common
        ArrayList<Vertex> common = new ArrayList<>();
        common.addAll(Arrays.asList(t[0].v));
        common.removeAll(vertices2);

        Vertex c1, c2;
        c1 = common.get(0);
        c2 = common.get(1);

        vertices1.removeAll(common);
        vertices2.removeAll(common);

        // vertices that are unique to the triangles
        Vertex v1, v2;
        v1 = vertices1.get(0);
        v2 = vertices2.get(0);

        // noem de oude edge (b,d).
        // de triangles {a,b,d}, {b,c,d} worden nu {a,c,d}, {b,c,a}
        e.v[0] = v1;
        e.v[1] = v2;
        e.t[0].v = new Vertex[]{v1, v2, c1};
        e.t[1].v = new Vertex[]{v1, v2, c2};
        this.t.add(new Triangle(v1, v2, c1));
        this.t.add(new Triangle(v1, v2, c2));
        this.removeTriangleSpec(t[0]);
        this.removeTriangleSpec(t[1]);
    }

    public Vertex findAdPoint(Vertex pr, Edge pipj) {
        // vertices of triangle 0
        ArrayList<Vertex> vertices1 = new ArrayList<>();
        vertices1.addAll(Arrays.asList(pipj.t[0].v));

        // vertices of triangle 1
        ArrayList<Vertex> vertices2 = new ArrayList<>();
        vertices2.addAll(Arrays.asList(pipj.t[1].v));

        vertices1.removeAll(vertices2);
        vertices2.removeAll(vertices1);

        Vertex c1, c2;

        c1 = vertices1.get(0);
        c2 = vertices2.get(0);
        if (c1 == pr) {
            return c2;
        } else {
            return c1;
        }
    }


    public void legalizeEdge(Vertex pr, Edge pipj) {
        // Check if pipj is not an outer edge
        if (pipj.t[1] == null || pipj.t[0] == null) {
            return;
        }

        Vertex ph = this.findAdPoint(pr, pipj); //find in edjusted triangle new point ph

        Edge edge = new Edge(pr, ph);
        Triangle th0 = new Triangle(pr, ph, pipj.v[0]);
        Triangle th1 = new Triangle(pr, ph, pipj.v[1]);
        edge.t[0] = th0;
        edge.t[1] = th1;

        if (ph == null) {
            return;
        }

        if (pipj.angle(ph) < Math.PI / 2 || pipj.angle2(ph) < Math.PI / 2) {
            return;
        }

        if (Math.min(pipj.smallestAngle(pr), pipj.smallestAngle(ph))
                < Math.min(edge.smallestAngle(pipj.v[0]), edge.smallestAngle(pipj.v[1]))) {
            
            //update the adusted edges
            for(Edge e: this.getEdges()){
                if(e.equals(pr, pipj.v[0])){
                    e.addTriangle(th0);
                } else if(e.equals(ph, pipj.v[0])){
                    e.addTriangle(th0);
                } else if(e.equals(pr, pipj.v[1])){
                    e.addTriangle(th1);
                } else if(e.equals(ph, pipj.v[1])){
                    e.addTriangle(th1);
                }
            }
            
            //flip edge
            this.removeEdge(pipj);
            this.addEdge(edge);
            //add new trinagles and remove old ones
            this.addTriangle(th0);
            this.addTriangle(th1);
            this.removeTriangleSpec(pipj.t[0]);
            this.removeTriangleSpec(pipj.t[1]);
            
            int j;
            if (pipj.t[0].v[0] == ph || pipj.t[0].v[1] == ph || pipj.t[0].v[2] == ph) {
                j = 1;
            } else {
                j = 0;
            }
            for (Edge e : this.getEdges()) {
                if ((e.t[0] == pipj.t[j] || e.t[1] == pipj.t[j]) && !e.equals(pipj)) {
                    this.legalizeEdge(pr, e);
                }
            }
        }
    }

    public Graph() {
        this.v = new HashSet<>();
        this.e = new HashSet<>();
        this.t = new HashSet<>();
    }

    public Graph(Graph graph) {
        this.v = graph.getVertices();
        this.e = graph.getEdges();
        this.t = graph.getTriangle();
    }

    public Set<Vertex> getVertices() {
        HashSet vertices = new HashSet<Vertex>();
        for (Vertex vertex : v) {
            vertices.add(vertex);
        }
        return vertices;
    }

    public Set<Edge> getEdges() {
        HashSet edges = new HashSet<Edge>();
        for (Edge edge : e) {
            edges.add(edge);
        }
        return edges;
    }

    public Set<Triangle> getTriangle() {
        HashSet triangles = new HashSet<Triangle>();
        for (Triangle tringle : t) {
            triangles.add(tringle);
        }
        return triangles;
    }

    public void addVertex(Vertex vertex) {
        this.v.add(vertex);
    }

    public void addTriangle(Triangle triangle) {
        this.t.add(triangle);
    }

    public boolean emptyTriangles() {
        return this.t.isEmpty();
    }

    public void removeTriangleSpec(Triangle t) {
        this.t.remove(t);
    }

    public void removeVertex(Vertex vertex) {
        this.v.remove(vertex);
        for (Edge e : this.getEdges()) {
            if (e.v[0] == vertex || e.v[1] == vertex) {
                this.removeEdge(e);
            }
        }
    }

    public void addEdge(Vertex v1, Vertex v2) {
        this.e.add(new Edge(v1, v2));
    }

    public void addEdge(Edge edge) {
        this.e.add(edge);
    }

    public void removeEdge(Edge e) {
        this.e.remove(e);
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) {
            return false;
        }
        Graph g = (Graph) o;

        if (v.size() != g.v.size()) {
            return false;
        }
        if (e.size() != g.e.size()) {
            return false;
        }

        ArrayList<int[]> pairs = new ArrayList<>();
        for (Edge edge : e) {
            int id1 = edge.v[0].getID();
            int id2 = edge.v[1].getID();
            int min = Math.min(id1, id2);
            int max = Math.max(id1, id2);
            pairs.add(new int[]{min, max});
        }
        for (Edge edge : g.e) {
            int id1 = edge.v[0].getID();
            int id2 = edge.v[1].getID();
            int min = Math.min(id1, id2);
            int max = Math.max(id1, id2);
            int pos = -1;
            for (int i = 0; i < pairs.size(); i++) {
                int[] z = pairs.get(i);
                if (z[0] == min && z[1] == max) {
                    pos = i;
                    i = pairs.size();
                }
            }
            if (pos == -1) {
                return false;
            }
            pairs.remove(pos);
        }
        return true;
    }

}
