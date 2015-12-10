package TrickyTriangles;

import GUI.GUI;
import Graph.RandomGraph;

public class TrickyTriangles {

    GUI gui;
    
    public void run(){
        gui = new GUI(this);
        RandomGraph g = new RandomGraph(100);
    }
    
    public static void main(String[] args) {
        new TrickyTriangles().run();
    }
    
}
