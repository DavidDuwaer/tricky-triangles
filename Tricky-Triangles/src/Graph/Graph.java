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
    
    public void flipEdge(Edge edge){
        Triangle[] t = edge.t;

        // Find the vertex unique to 0
        ArrayList<Vertex> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(t[0].v));
        temp.removeAll(Arrays.asList(t[1].v));
        Vertex v1 = temp.get(0);
        
        // Find the common vertices
        temp = new ArrayList<>();
        temp.addAll(Arrays.asList(t[0].v));
        temp.remove(v1);
        Vertex c1 = temp.get(0);
        Vertex c2 = temp.get(1);
        
        // Find the vertex unique to 1
        temp = new ArrayList<>();
        temp.addAll(Arrays.asList(t[1].v));
        temp.remove(c1);
        temp.remove(c2);
        Vertex v2 = temp.get(0);
        
        // de triangles {c1,c2,v1}, {c1,c2,v2} worden nu {c1,v2,v1}, {v1,c2,v2}

        edge.v[0] = v1;
        edge.v[1] = v2;
        
        for(Edge edge2: e){
            if(edge2.t.length > 0){
                for(int i = 0; i < 2; i++){
                    if(edge2.t[i].contains(c1,c2,v1) || edge2.t[i].contains(c1,c2,v2)){
                        if(edge2.contains(c1)){
                            edge2.t[i].v = new Vertex[] {v1,v2,c1};
                        } else {
                            edge2.t[i].v = new Vertex[] {v1,v2,c2};
                        }
                    }
                }
            }
        }
        
        edge.t[0].v = new Vertex[] {v1,v2,c1};
        edge.t[1].v = new Vertex[] {v1,v2,c2};
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
