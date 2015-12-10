package TrickyTriangles;

import GUI.GUI;
import Graph.Graph;
import Graph.GraphFactory;
import Graph.Vertex;
import java.util.Set;
import java.util.TreeSet;

public class TrickyTriangles {

    /********************************
     * Global parameters
     ********************************/
    /*
     * Amount of vertices
     */
    int n;
    
    GUI gui;
    GraphFactory graphFactory;
    Graph graph;
    
    public void run(){
        gui = new GUI(this);
        
        graph = initGraph();
    }
    
    /*
     * Create a random point set with a random triangulation. All poitns
     * have positions (x,y) such that 0<=x<=1 and 0<=y<=1.
     */
    private Graph initGraph()
    {
        Set<Vertex> vertexSet = createRandomVertexSet();
        return graphFactory.createRandomTriangulation(vertexSet);
    }
    
    private Set<Vertex> createRandomVertexSet()
    {
        Set<Vertex> set = new TreeSet<>();
        for (int i = 0; i < n; i++)
        {
            set.add(graphFactory.createRandomVertex());
        }
        return set;
    }
    
    public static void main(String[] args) {
        new TrickyTriangles().run();
    }
    
}
