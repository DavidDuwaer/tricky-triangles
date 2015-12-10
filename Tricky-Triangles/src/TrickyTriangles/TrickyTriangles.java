package TrickyTriangles;

import GUI.GUI;
import Graph.Graph;

public class TrickyTriangles {

    GUI gui;
    
    public void run(){
        gui = new GUI(this);
        Graph g = new Graph(100);
    }
    
    public static void main(String[] args) {
        new TrickyTriangles().run();
    }
    
}
