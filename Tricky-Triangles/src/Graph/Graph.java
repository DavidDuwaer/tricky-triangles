package Graph;

import GUI.GUI;
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

    public void flipEdge(Edge edge){
        Triangle[] tr = edge.t;

        System.out.println("Trying to flip " + edge.v[0].getID() + ", " + edge.v[1].getID());
        for(Vertex v: tr[0].v){
            System.out.print("(" + v.getID() + ", " + v.getx() + ", " + v.gety() + ") ");
        }
        System.out.println("");
        for(Vertex v: tr[1].v){
            System.out.print("(" + v.getID() + ", " + v.getx() + ", " + v.gety() + ") ");
        }
        System.out.println("");
        // Find the vertex unique to 0
        ArrayList<Vertex> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(tr[0].v));
        temp.removeAll(Arrays.asList(tr[1].v));
        Vertex v1 = temp.get(0);
        if(temp.size() > 1){
            System.out.println("PANIEK! size temp ");
            return;
        }
        
        // Find the common vertices
        temp = new ArrayList<>();
        temp.addAll(Arrays.asList(tr[0].v));
        temp.remove(v1);
        Vertex c1 = temp.get(0);
        Vertex c2 = temp.get(1);
        
        // Find the vertex unique to 1
        temp = new ArrayList<>();
        temp.addAll(Arrays.asList(tr[1].v));
        temp.remove(c1);
        temp.remove(c2);
        if(temp.size() > 1){
            System.out.println("PANIEK! size temp 2");
            return;
        }
        Vertex v2 = temp.get(0);
        
        // de triangles {c1,c2,v1}, {c1,c2,v2} worden nu {c1,v2,v1}, {v1,c2,v2}

        edge.v[0] = v1;
        edge.v[1] = v2;
        
        Triangle newT1 = new Triangle(v1,v2,c1, getEdge(v1.getID(), v2.getID()), getEdge(v2.getID(), c1.getID()), getEdge(c1.getID(), v1.getID()));
        Triangle newT2 = new Triangle(v1,v2,c2, getEdge(v1.getID(), v2.getID()), getEdge(v2.getID(), c2.getID()), getEdge(c2.getID(), v1.getID()));
        t.add(newT1);
        t.add(newT2);
        
        for(Edge edge2: e){
            if(edge2.t.length > 0){
                for(int i = 0; i < 2; i++){
                    if(edge2.t[i] != null){
                        if(edge2.t[i].contains(c1,c2,v1) || edge2.t[i].contains(c1,c2,v2)){
                            t.remove(edge2.t[i]);
                            if(edge2.contains(c1)){
                                edge2.t[i] = newT1;
                            } else {
                                edge2.t[i] = newT2;
                            }
                        }
                    }
                }
            }
        }
        edge.t[0] = newT1;
        edge.t[1] = newT2;
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

    Vertex findThird(Vertex[] vert, Vertex v2, Vertex v3){
        for(Vertex v: vert){
            if(v.getID() != v2.getID() && v.getID() != v3.getID()){
                return v;
            }
        }
        return null;
    }

    boolean illegalEdge(Edge pipj){
        Vertex v2 = pipj.v[0];
        Vertex v3 = pipj.v[1];
        Vertex v1 = findThird(pipj.t[0].v, v2, v3);
        Vertex v4 = findThird(pipj.t[1].v, v2, v3);
        if(inTriangle(v2, v1, v3, v4) || inTriangle(v3,v1,v2,v4)){
            System.out.println("(" + pipj.v[0].getID() + ", " + pipj.v[1].getID() + ") is géén illegal edge (v2)");
            return false;
        }
        double x1 = v1.getx(), x2 = v2.getx(), x3 = v3.getx(), x4 = v4.getx();
        double y1 = v1.gety(), y2 = v2.gety(), y3 = v3.gety(), y4 = v4.gety();
        double a = -2*x1+2*x3;
        double b = -2*x2+2*x3;
        double c = -2*y1+2*y3;
        double d = -2*y2+2*y3;
        double e = x1*x1-x3*x3+y1*y1-y3*y3;
        double f = x2*x2-x3*x3+y2*y2-y3*y3;
        double k = (b*e-a*f)/(a*d-b*c);
        double h = (c*f-d*e)/(a*d-b*c);
        double rSquared = (x1-h)*(x1-h) + (y1-k)*(y1-k);
        System.out.println("Found circle: " + h + ", " + k + ", " + rSquared);
        if((x4-h)*(x4-h) + (y4-k)*(y4-k) < rSquared){
            System.out.println("(" + pipj.v[0].getID() + ", " + pipj.v[1].getID() + ") is een illegal edge");
            return true;
        } else {
            System.out.println("(" + pipj.v[0].getID() + ", " + pipj.v[1].getID() + ") is géén illegal edge t.o.v. " + v1.getID() + " en " + v4.getID());
            return false;
        }
    }
    
    boolean inTriangle(Vertex p, Vertex v1, Vertex v2, Vertex v3){
        Triangle t = new Triangle(v1,v2,v3,null,null,null);
        return t.PointInTriangle(p);
    }

    public void legalizeEdge(Vertex pr, Edge pipj) {
        // Check if pipj is not an outer edge
        if (pipj == null || pipj.t == null || pipj.t[1] == null || pipj.t[0] == null) {
            System.out.println("(" + pipj.v[0].getID() + ", " + pipj.v[1].getID() + ") is géén illegal edge (v3)");
            return;
        }
        
        // check if 
        if(illegalEdge(pipj)){
            flipEdge(pipj);
            Triangle[] t1 = pipj.t;
            for(Triangle tr: t1){
                for(Edge ed: tr.e){
                    //TODO niet alles
                    if(ed != pipj)
                        legalizeEdge(pr, ed);
                }
            }
        }
        
        /*
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
        }*/
    }

    public Graph() {
        this.v = new HashSet<>();
        this.e = new HashSet<>();
        this.t = new HashSet<>();
    }

    public Graph(Graph graph) {
        this.v = deepCopyV(graph.getVertices());
        this.e = deepCopyE(graph.getEdges());
        this.t = deepCopyT(graph.getTriangle());
    }

    public Set<Vertex> deepCopyV(Set<Vertex> verts){
        Set<Vertex> toRet = new HashSet<>();
        for(Vertex v: verts){
            Vertex v2 = new Vertex(v.getx(), v.gety(), v.getID());
            toRet.add(v2);
        }
        return toRet;
    }
    
    public Set<Edge> deepCopyE(Set<Edge> edges){
        Set<Edge> toRet = new HashSet<>();
        for(Edge e: edges){
            Edge e2 = new Edge(getVertex(e.v[0].getID()), getVertex(e.v[1].getID()));
            toRet.add(e2);
        }
        return toRet;
    }
    
    public Set<Triangle> deepCopyT(Set<Triangle> triangles){
        Set<Triangle> toRet = new HashSet<>();
        for(Triangle t: triangles){
            if(t.v[0] != null && t.v[1] != null && t.v[2] != null && t.e[0] != null && t.e[1] != null && t.e[2] != null){
                Triangle t2 = new Triangle(getVertex(t.v[0].getID()),getVertex(t.v[1].getID()),getVertex(t.v[2].getID()),getEdge(t.e[0].v[0].getID(), t.e[0].v[1].getID()),getEdge(t.e[1].v[0].getID(), t.e[1].v[1].getID()),getEdge(t.e[2].v[0].getID(), t.e[2].v[1].getID()));
                toRet.add(t2);
            }
        }
        return toRet;
    }
    
    public Vertex getVertex(int id){
        for(Vertex vert: v){
            if(vert.getID() == id){
                return vert;
            }
        }
        System.out.println("Paniek! " + id);
        return null;
    }
    
    public Edge getEdge(int id1, int id2){
        for(Edge edge: e){
            if((edge.v[0].getID() == id1 || edge.v[1].getID() == id1) && (edge.v[0].getID() == id2 || edge.v[1].getID() == id2)){
                return edge;
            }
        }
        System.out.println("Paniek! " + id1 + " " + id2);
        return null;
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
