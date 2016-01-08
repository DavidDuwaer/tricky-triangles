package Graph;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author s115869
 */
public class Edge {
    
    Vertex[] v = new Vertex[2];
    Triangle[] t = new Triangle[2];
    
    public Edge(Vertex v1, Vertex v2)
    {
        v = new Vertex[2];
        v[0] = v1;
        v[1] = v2;
    }
    
    public Vertex[] getVertices()
    {
        return v;
    }
    
    public void addTriangle(Triangle t){
        if(this.t[0] == null){
            this.t[0] = t;
        } else {
            this.t[1] = t;
        }
    }
    
    double smallestAngle(Vertex vertex){
        double a = Math.sqrt(Math.pow(this.v[0].getx() - this.v[1].getx(), 2) +
                Math.pow(this.v[0].gety() - this.v[1].gety(), 2));
        double b = Math.sqrt(Math.pow(this.v[0].getx() - vertex.getx(), 2) +
                Math.pow(this.v[0].gety() - vertex.gety(), 2));
        double c = Math.sqrt(Math.pow(this.v[1].getx() - vertex.getx(), 2) +
                Math.pow(this.v[1].gety() - vertex.gety(), 2));
        double B = Math.acos((Math.pow(a, 2)+Math.pow(c, 2) - Math.pow(b, 2))/2*a*c);
        double C = Math.acos((Math.pow(b, 2)+Math.pow(a, 2) - Math.pow(c, 2))/2*b*a);
        return Math.min(C, B);
    }
}
