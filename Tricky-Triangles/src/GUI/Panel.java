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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.StrokeBorder;

/**
 *
 * @author s115869
 */
public class Panel extends JPanel implements MouseListener {

    Graph graph;
    JFrame frame;
    Graph targetGraph;

    Panel(Graph graph) {
        this.graph = graph;
        setBorder(new StrokeBorder(new BasicStroke(1f)));
        setBackground(new Color(91, 155, 213));
    }

    Panel(Graph g, Graph tg, JFrame f){
        graph = g;
        targetGraph = tg;
        frame = f;
        setBorder(new StrokeBorder(new BasicStroke(1f)));
        setBackground(new Color(91, 155, 213));
        this.addMouseListener(this);
    }
    
    public void set(Graph g) {
        graph = g;
    }
    
    /*
     * Draw graph
     */
    double debugMult = 0.8;
    int debugOffset = 6;
    
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
                    (int) Math.floor((double) getWidth() * 0.1 + edge.getVertices()[0].getPos()[0] * getWidth() * debugMult) + debugOffset,
                    (int) Math.floor((double) getHeight() * 0.1 + edge.getVertices()[0].getPos()[1] * getHeight() * debugMult) + debugOffset,
                    (int) Math.floor((double) getWidth() * 0.1 + edge.getVertices()[1].getPos()[0] * getWidth() * debugMult) + debugOffset,
                    (int) Math.floor((double) getHeight() * 0.1 + edge.getVertices()[1].getPos()[1] * getHeight() * debugMult + debugOffset)
            );
        }
        /*
        int i = 0;
<<<<<<< HEAD
        for (Triangle t : graph.getTriangle()) {
            double a = Math.sqrt(Math.pow(t.getTriangles()[0].getx() - t.getTriangles()[1].getx(), 2) +
                Math.pow(t.getTriangles()[0].gety() - t.getTriangles()[1].gety(), 2));
        double b = Math.sqrt(Math.pow(t.getTriangles()[0].getx() - t.getTriangles()[2].getx(), 2) +
                Math.pow(t.getTriangles()[0].gety() - t.getTriangles()[2].gety(), 2));
        double c = Math.sqrt(Math.pow(t.getTriangles()[1].getx() - t.getTriangles()[2].getx(), 2) +
                Math.pow(t.getTriangles()[1].gety() - t.getTriangles()[2].gety(), 2));
            g.setColor(Color.black);
            double rad = (a*b*c)/(Math.sqrt((a+b+c)*(b+c-a)*(c+a-b)*(a+b-c)));
            graphics.drawOval(10+10*i, 10+10*i, 
                    (int) Math.floor(getWidth()*0.8*rad), 
                    (int) Math.floor(getWidth()*0.8*rad));
            i++;
        }*/
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

        int debugCircleSize = 12;
        for (Vertex vertex : graph.getVertices()) {
            
            g.setColor(Color.black);
            graphics.fillRoundRect(
                    (int) Math.floor((double) getWidth() * 0.1 + vertex.getPos()[0] * (double) getWidth() * debugMult)+debugOffset-debugCircleSize/2,
                    (int) Math.floor((double) getHeight() * 0.1 + vertex.getPos()[1] * (double) getHeight() * debugMult)+debugOffset-debugCircleSize/2,
                    debugCircleSize,
                    debugCircleSize,
                    debugCircleSize,
                    debugCircleSize);
            g.setColor(new Color(91, 155, 213));
            graphics.fillRoundRect(
                    (int) Math.floor((double) getWidth() * 0.1 + vertex.getPos()[0] * (double) getWidth() * debugMult + 2),
                    (int) Math.floor((double) getHeight() * 0.1 + vertex.getPos()[1] * (double) getHeight() * debugMult + 2),
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
    public void mousePressed(MouseEvent ev) {
        int x0 = ev.getX();
        int y0 = ev.getY();
        double x = (x0 - debugOffset - getWidth() * 0.1)/(getWidth() * debugMult);
        double y = (y0 - debugOffset - getHeight() * 0.1)/(getHeight() * debugMult);
        System.out.println(x + " " + y);
        Vertex v = new Vertex(x,y,-1);
        Edge bestEdge = null;
        double bestDist = 1000;
        for(Edge e: graph.getEdges()){
            if(dist(v,e) < bestDist){
                bestDist = dist(v,e);
                bestEdge = e;
            }
        }
        if(graph.canFlipEdge(bestEdge)){
            graph.flipEdge(bestEdge);
            repaint();
            if(gameOver()){
                Object[] options = {"Good show!"};
                int n = JOptionPane.showOptionDialog(frame,
                "You, good sir, just won!",
                "Heap heap... array!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            }
        }
    }
    
    boolean gameOver(){
        return graph.equals(targetGraph);
    }
    
    double dist (Vertex p, Edge e) {
        Vertex q1 = e.getVertices()[0];
        Vertex q2 = e.getVertices()[1];
        if ( inp( sub(p, q1), sub(q2 , q1)) < 0) {
            return Math.sqrt( inp( sub(p, q1), sub(p, q1)));
        }
        if ( inp( sub(p, q2), sub(q1 , q2)) < 0) {
            return Math.sqrt ( inp( sub(p, q2), sub(p, q2)));
        }
        return Math.abs ( dakje( sub(p, q1), sub(q2 , q1))) / Math.sqrt ( inp( sub(q2 , q1), sub(q2 , q1)));
    }
    
    double inp(Vertex v1, Vertex v2){
        return v1.getx()*v2.getx() + v1.gety()*v2.gety();
    }

    Vertex sub(Vertex v1, Vertex v2){
        return new Vertex(v1.getx()-v2.getx(), v1.gety()-v2.gety(), -1);
    }
    
    double dakje(Vertex v1, Vertex v2){
        return v1.getx()*v2.gety()-v1.gety()*v2.getx();
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
