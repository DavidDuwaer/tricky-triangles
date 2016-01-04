package Graph;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author s115869
 */
public class Edge {
    
    Vertex[] v;
    Triangle[] t;
    
    public Edge(Vertex v1, Vertex v2)
    {
        v = new Vertex[2];
        v[0] = v1;
        v[1] = v2;
    }
    
    public Vertex[] getVertices()
    {
        return v;
    }
}
