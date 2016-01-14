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
    
    boolean contains(Vertex w) {
        for(Vertex vert: v){
            if(vert.getID() == w.getID()){
                return true;
            }
        }
        return false;
    }

    
    public Vertex[] getVertices()
    {
        return v;
    }
    
    public boolean equals(Vertex v1, Vertex v2){
        if(this.v[0] == v1 && this.v[1] == v2){
            return true;
        } else if(this.v[0] == v1 && this.v[1] == v2){
            return true;
        } else {
            return false;
        }
    }
    
    public void replace(Triangle t1, Triangle t2){
        if(this.t[0] == t1){
            this.t[0] = t2;
        } else {
            this.t[1] = t2;
        }
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
    double angle(Vertex vertex){
        double a = Math.sqrt(Math.pow(this.v[0].getx() - this.v[1].getx(), 2) +
                Math.pow(this.v[0].gety() - this.v[1].gety(), 2));
        double b = Math.sqrt(Math.pow(this.v[0].getx() - vertex.getx(), 2) +
                Math.pow(this.v[0].gety() - vertex.gety(), 2));
        double c = Math.sqrt(Math.pow(this.v[1].getx() - vertex.getx(), 2) +
                Math.pow(this.v[1].gety() - vertex.gety(), 2));
        double B = Math.acos((Math.pow(a, 2)+Math.pow(c, 2) - Math.pow(b, 2))/2*a*c);
        double C = Math.acos((Math.pow(b, 2)+Math.pow(a, 2) - Math.pow(c, 2))/2*b*a);
        return C;
    }
    double angle2(Vertex vertex){
        double a = Math.sqrt(Math.pow(this.v[0].getx() - this.v[1].getx(), 2) +
                Math.pow(this.v[0].gety() - this.v[1].gety(), 2));
        double b = Math.sqrt(Math.pow(this.v[0].getx() - vertex.getx(), 2) +
                Math.pow(this.v[0].gety() - vertex.gety(), 2));
        double c = Math.sqrt(Math.pow(this.v[1].getx() - vertex.getx(), 2) +
                Math.pow(this.v[1].gety() - vertex.gety(), 2));
        double B = Math.acos((Math.pow(a, 2)+Math.pow(c, 2) - Math.pow(b, 2))/2*a*c);
        double C = Math.acos((Math.pow(b, 2)+Math.pow(a, 2) - Math.pow(c, 2))/2*b*a);
        return B;
    }
}
