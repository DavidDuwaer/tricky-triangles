package TrickyTriangles;

import GUI.GUI;
import Graph.Graph;
import Graph.GraphFactory;
import Graph.Vertex;
import java.util.HashSet;
import java.util.Set;

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
        n = 5;
        
        graphFactory = new GraphFactory();
        
        gui = new GUI(this);
        
        graph = initGraph();
    }
    
    /*
     * Create a random point set with a random triangulation. All poitns
     * have positions (x,y) such that 0<=x<=1 and 0<=y<=1.
     */
    private Graph initGraph()
    {
        Set<Vertex> pointSet = createRandomPointSet();
        return graphFactory.createRandomTriangulation(pointSet);
    }
    
    private Set<Vertex> createRandomPointSet()
    {
        Set<Vertex> set = new HashSet<>();
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
