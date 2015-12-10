package geometricalgorithms;

import GUI.GUI;
import Graph.Graph;

public class GeometricAlgorithms {

    GUI gui;
    
    public void run(){
        gui = new GUI(this);
        Graph g = new Graph(100);
    }
    
    public static void main(String[] args) {
        new GeometricAlgorithms().run();
    }
    
}
