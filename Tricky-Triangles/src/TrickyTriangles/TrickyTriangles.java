package TrickyTriangles;

import GUI.GUI;
import Graph.Graph;
import Graph.GraphFactory;

public class TrickyTriangles {

    GUI gui;
    GraphFactory graphFactory;
    
    public void run(){
        gui = new GUI(this);
        Graph g = new Graph();
    }
    
    public static void main(String[] args) {
        new TrickyTriangles().run();
    }
    
}
