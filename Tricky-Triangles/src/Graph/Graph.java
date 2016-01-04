package Graph;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author s115869
 */
public class Graph {
    
    Set<Vertex> v;
    Set<Edge> e;
    
    // TODO Flip the given edge
    public void flipEdge(Edge e){
        Triangle[] t = e.t;
        Vertex[][] vertices = {t[0].v, t[1].v};
        boolean[][] same = new boolean[2][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(vertices[0][i] == vertices[1][j]){
                    same[0][i] = true;
                    same[1][j] = true;
                }
            }
        }
        Vertex v1, v2;
        for(int i = 0; i < 3; i++){
            if(!same[0][i]){
                v1 = vertices[0][i];
            }
            if(!same[1][i]){
                v2 = vertices[1][i];
            }
        }
        // v1 en v2 zijn nu de nieuwe vertices van de te flippen edge.
    }
    
    // TODO generates random graph with 'vertices' vertices.
    public Graph(){
        this.v = new HashSet<>();
        this.e = new HashSet<>();
    }
}
