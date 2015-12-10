package GUI;

import Graph.Graph;
import TrickyTriangles.TrickyTriangles;
import javax.swing.JFrame;

/**
 *
 * @author s115869
 */
public class GUI {
    
    Panel panel;
    JFrame frame;
    
    public GUI(TrickyTriangles main){
        panel = new Panel(main);
        panel.addMouseListener(panel);
        frame = new JFrame("Geometric algorithms - the triangulation game");
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(1600,875); 
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().setBackground( Color.BLACK );
    }
    
    public void show(Graph g){
        panel.set(g);
        panel.repaint();
    }
    
}
