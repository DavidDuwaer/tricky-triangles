package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author s115869
 */
public class Graph {
    
    private Set<Vertex> v;
    private Set<Edge> e;
    
    public void flipEdge(Edge e){
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
        e.t[0].v = new Vertex[] {v1,v2,c1};
        e.t[1].v = new Vertex[] {v1,v2,c2};
    }
    
    public void legalizeEdge(Vertex v1, Vertex v2, Vertex v3, ArrayList<Vertex> T){
        if(true /*illigale */){
            flipEdge(new Edge(v1,v2));
        }
    }
    
    public Graph(){
        this.v = new HashSet<>();
        this.e = new HashSet<>();
    }
    
    public Graph(Graph graph)
    {
        this.v = graph.getVertices();
        this.e = graph.getEdges();
    }
    
    public Set<Vertex> getVertices()
    {
        HashSet vertices = new HashSet<Vertex>();
        for (Vertex vertex : v)
        {
            vertices.add(vertex);
        }
        return vertices;
    }
    
    public Set<Edge> getEdges()
    {
        HashSet edges = new HashSet<Edge>();
        for (Edge edge : e)
        {
            edges.add(edge);
        }
        return edges;
    }
    
    public void addVertex(Vertex vertex)
    {
        this.v.add(vertex);
    }
    
    public void addEdge(Vertex v1, Vertex v2)
    {
        this.e.add(new Edge(v1, v2));
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass())
            return false;
        Graph g = (Graph) o;
        
        if(v.size() != g.v.size()){
            return false;
        }
        if(e.size() != g.e.size()){
            return false;
        }
        
        ArrayList<int[]> pairs = new ArrayList<>();
        for(Edge edge: e){
            int id1 = edge.v[0].getID();
            int id2 = edge.v[1].getID();
            int min = Math.min(id1,id2);
            int max = Math.max(id1,id2);
            pairs.add(new int[]{min, max});
        }
        for(Edge edge: g.e){
            int id1 = edge.v[0].getID();
            int id2 = edge.v[1].getID();
            int min = Math.min(id1,id2);
            int max = Math.max(id1,id2);
            int pos = -1;
            for(int i = 0; i < pairs.size(); i++){
                int[] z = pairs.get(i);
                if(z[0] == min && z[1] == max){
                    pos = i;
                    i = pairs.size();
                }
            }
            if(pos == -1){
                return false;
            }
            pairs.remove(pos);
        }
        return true;
    }
    
}
