package GUI;

import Graph.RandomGraph;
import TrickyTriangles.TrickyTriangles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author s115869
 */
public class Panel extends JPanel implements MouseListener{
    
    RandomGraph graph;
    TrickyTriangles main;
    
    Panel(TrickyTriangles m){
        main = m;
    }
    
    public void set(RandomGraph g){
        graph = g;
    }
    
    // Draw graph
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    // If edge is clicked, flip it
    // TODO
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
