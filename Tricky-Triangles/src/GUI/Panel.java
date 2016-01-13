package GUI;

import Graph.Edge;
import Graph.Graph;
import Graph.Vertex;
import Graph.Triangle;
import TrickyTriangles.TrickyTriangles;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
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
public class Panel extends JPanel implements MouseListener {

    Graph graph;

    Panel(Graph graph) {
        this.graph = graph;
        setBorder(new StrokeBorder(new BasicStroke(1f)));
        setBackground(new Color(91, 155, 213));

        /*
         * Set click listener
         */
    }

    public void set(Graph g) {
        graph = g;
    }
    
    /*
     * Draw graph
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        for (Edge edge : graph.getEdges()) {
            g.setColor(Color.black);
            graphics.drawLine(
                    (int) Math.floor((double) getWidth() * 0.1 + edge.getVertices()[0].getPos()[0] * getWidth() * 0.8) + 6,
                    (int) Math.floor((double) getHeight() * 0.1 + edge.getVertices()[0].getPos()[1] * getHeight() * 0.8) + 6,
                    (int) Math.floor((double) getWidth() * 0.1 + edge.getVertices()[1].getPos()[0] * getWidth() * 0.8) + 6,
                    (int) Math.floor((double) getHeight() * 0.1 + edge.getVertices()[1].getPos()[1] * getHeight() * 0.8 + 6)
            );
        }
        int i = 0;
//        for (Triangle t : graph.getTriangle()) {
//            double a = Math.sqrt(Math.pow(t.getTriangles()[0].getx() - t.getTriangles()[1].getx(), 2) +
//                Math.pow(t.getTriangles()[0].gety() - t.getTriangles()[1].gety(), 2));
//        double b = Math.sqrt(Math.pow(t.getTriangles()[0].getx() - t.getTriangles()[2].getx(), 2) +
//                Math.pow(t.getTriangles()[0].gety() - t.getTriangles()[2].gety(), 2));
//        double c = Math.sqrt(Math.pow(t.getTriangles()[1].getx() - t.getTriangles()[2].getx(), 2) +
//                Math.pow(t.getTriangles()[1].gety() - t.getTriangles()[2].gety(), 2));
//            g.setColor(Color.black);
//            double rad = (a*b*c)/(Math.sqrt((a+b+c)*(b+c-a)*(c+a-b)*(a+b-c)));
//            graphics.drawOval(10+10*i, 10+10*i, 
//                    (int) Math.floor(getWidth()*0.8*rad), 
//                    (int) Math.floor(getWidth()*0.8*rad));
//            i++;
//        }

        for (Vertex vertex : graph.getVertices()) {

            g.setColor(Color.black);
            graphics.fillRoundRect(
                    (int) Math.floor((double) getWidth() * 0.1 + vertex.getPos()[0] * (double) getWidth() * 0.8),
                    (int) Math.floor((double) getHeight() * 0.1 + vertex.getPos()[1] * (double) getHeight() * 0.8),
                    12,
                    12,
                    12,
                    12);
            g.setColor(new Color(91, 155, 213));
            graphics.fillRoundRect(
                    (int) Math.floor((double) getWidth() * 0.1 + vertex.getPos()[0] * (double) getWidth() * 0.8 + 2),
                    (int) Math.floor((double) getHeight() * 0.1 + vertex.getPos()[1] * (double) getHeight() * 0.8 + 2),
                    8,
                    8,
                    8,
                    8);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    // If edge is clicked, flip it
    // TODO
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
