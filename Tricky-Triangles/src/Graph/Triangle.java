package Graph;

/**
 *
 * @author s115869
 */
public class Triangle {

    Vertex[] v = new Vertex[3];
    Edge[] e = new Edge[2];

    public Triangle(Vertex v1, Vertex v2, Vertex v3) {
        v = new Vertex[3];
        this.v[0] = v1;
        this.v[1] = v2;
        this.v[2] = v3;
    }
    public Vertex[] getTriangles()
    {
        return v;
    }

    double sign(Vertex p1, Vertex p2, Vertex p3) {
        return (p1.getx() - p3.getx()) * (p2.gety() - p3.gety()) - (p2.getx() - p3.getx()) * (p1.gety() - p3.gety());
    }

    boolean PointInTriangle(Vertex pt) {
        boolean b1, b2, b3;

        b1 = sign(pt, this.v[0], this.v[1]) < 0;
        b2 = sign(pt, this.v[1], this.v[2]) < 0;
        b3 = sign(pt, this.v[2], this.v[0]) < 0;

        return ((b1 == b2) && (b2 == b3));
    }

    boolean PointOnTriangle(Vertex pt) {
        boolean b1, b2, b3;

        b1 = sign(pt, this.v[0], this.v[1]) < 0;
        b2 = sign(pt, this.v[1], this.v[2]) < 0;
        b3 = sign(pt, this.v[2], this.v[0]) < 0;

        return ((b1 == b2) && (b2 == b3));
    }

    double smallestAngle() {
        double a = Math.sqrt(Math.pow(this.v[0].getx() - this.v[1].getx(), 2) +
                Math.pow(this.v[0].gety() - this.v[1].gety(), 2));
        double b = Math.sqrt(Math.pow(this.v[0].getx() - this.v[2].getx(), 2) +
                Math.pow(this.v[0].gety() - this.v[2].gety(), 2));
        double c = Math.sqrt(Math.pow(this.v[1].getx() - this.v[2].getx(), 2) +
                Math.pow(this.v[1].gety() - this.v[2].gety(), 2));
        
        double A = Math.acos((Math.pow(b, 2)+Math.pow(c, 2) - Math.pow(a, 2))/2*b*c);
        double B = Math.acos((Math.pow(a, 2)+Math.pow(c, 2) - Math.pow(b, 2))/2*a*c);
        double C = Math.acos((Math.pow(b, 2)+Math.pow(a, 2) - Math.pow(c, 2))/2*b*a);
        return Math.min(A, Math.min(C, B));
    }
    
    
}
