package GUI;

import Graph.Graph;
import Graph.Vertex;
import TrickyTriangles.TrickyTriangles;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.border.StrokeBorder;

/**
 *
 * @author s115869
 */
public class Panel extends JPanel implements MouseListener{
    
    Graph graph;
    
    Panel(Graph graph){
        this.graph = graph;
        setBorder(new StrokeBorder(new BasicStroke(1f)));
    }
    
    public void set(Graph g){
        graph = g;
    }
    
    // Draw graph
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for(Vertex vertex : graph.getVertices())
        {
            graphics.fillRoundRect(
                    (int) Math.floor((double) getWidth() * 0.1 + vertex.getPos()[0] * (double) getWidth() * 0.8),
                    (int) Math.floor((double) getHeight() * 0.1 + vertex.getPos()[1] * (double) getHeight() * 0.8),
                    12,
                    12,
                    12,
                    12);
//            g.drawOval(
//                    (int) Math.floor(vertex.getPos()[0] * (double) getWidth()),
//                    (int) Math.floor(vertex.getPos()[1] * (double) getHeight()),
//                    (int) Math.floor(vertex.getPos()[0] * (double) getWidth()) + g.,
//                    (int) Math.floor(vertex.getPos()[1] * (double) getHeight()) + 2
//            );
//            g.drawOval(
//                    (int) (vertex.getPos()[0] * getWidth()),
//                    (int) (vertex.getPos()[1] * getHeight()),
//                    (int) (vertex.getPos()[0] * getWidth()) + 1,
//                    (int) (vertex.getPos()[1] * getHeight()) + 1
//            );
        }
//        g.drawOval(0, 0, getWidth(), getHeight());
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
