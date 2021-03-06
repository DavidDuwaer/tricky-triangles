package TrickyTriangles;

import GUI.GUI;
import Graph.Graph;
import Graph.GraphFactory;
import Graph.Triangle;
import Graph.Vertex;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TrickyTriangles {

    /**
     * ******************************
     * Global parameters ******************************
     */

    GUI gui;
    GraphFactory graphFactory;
    Graph workGraph;
    Graph workGraphInitial;
    Graph goalGraph;
    
    /*
     * Game mode
     */
    public enum Mode
    {
        MOVE_TO_TARGET,
        DELAUNAY_GAME
    }
    private Mode mode;
    
    /*
     * Amount of vertices
     */
    private int n;
    
    /*
     * Edge affirmation on (false = off)
     */
    private boolean edgeAff;
    
    public void run() {
        n = 5;
        edgeAff = false;
        mode = Mode.MOVE_TO_TARGET;

        graphFactory = new GraphFactory();

        newGame();

        gui = new GUI(this);
    }
    
    /*
     * Resets both the goal graph and the work graph. New graphs are
     * generated according to the current settings.
     */
    public void newGame()
    {
        Set<Vertex> pointSet = graphFactory.createRandomPointSet(n);
        //workGraph = graphFactory.createGraphWithOnlyPoints(pointSet);
        workGraph = graphFactory.createRandomTriangulation(pointSet);
        //workGraph = graphFactory.createDelaunayTriangulation(pointSet);
        workGraphInitial = new Graph(workGraph); // make a copy of workGraph
        //goalGraph = graphFactory.createRandomGraphFromPoints(pointSet);
        if (mode == Mode.DELAUNAY_GAME)
            goalGraph = graphFactory.createDelaunayTriangulation(pointSet);
        else
            goalGraph = graphFactory.createRandomTriangulation(pointSet);
//        System.out.println("New game called");
//        for (Vertex v : workGraph.getVertices())
//        {
//            System.out.print("v(" + v.getPos()[0] + ", " + v.getPos()[1] + ") ");
//        }
//        System.out.print("\n");
    }
    
    /*
     * Sets the work graph to what it initially was.
     */
    public void resetGame()
    {
        workGraph = new Graph(workGraphInitial); // make a copy of workGraphInitial
    }
    
    public Graph getWorkGraph() {
        return workGraph;
    }
    
    public Graph getWorkGraphInitial() {
        return workGraphInitial;
    }

    public Graph getGoalGraph() {
        return goalGraph;
    }
    
    public void setMode(Mode mode)
    {
        this.mode = mode;
    }
    
    public Mode getMode()
    {
        return this.mode;
    }
    
    public void setNoPoints(int n)
    {
        this.n = n;
    }
    
    public int getNoPoints()
    {
        return this.n;
    }
    
    public void setEdgeAff(boolean edgeAff)
    {
        this.edgeAff = edgeAff;
    }
    
    public boolean getEdgeAff()
    {
        return this.edgeAff;
    }

    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrickyTriangles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TrickyTriangles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TrickyTriangles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TrickyTriangles.class.getName()).log(Level.SEVERE, null, ex);
        }
        new TrickyTriangles().run();
    }

}
