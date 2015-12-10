package Graph;

import java.util.ArrayList;

/**
 *
 * @author s115869
 */
public class Graph {
    
    public Vertex[] v;
    
    // TODO Flip the given edge
    public void flipEdge(Edge e){
        Triangle[] t = e.t;
        Vertex[][] vertices = {t[0].v, t[1].v};
        boolean[][] same = new boolean[2][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(vertices[0][i].id == vertices[1][j].id){
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
    
    // TODO
    public Edge[] getEdges(){
        ArrayList<Edge> edges = new ArrayList<>();
        for(Vertex vert: v){
            for(Edge e: vert.e){
                edges.add(e);
            }
        }
        return edges.toArray();
    }
    
}
