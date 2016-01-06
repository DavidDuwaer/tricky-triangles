package Graph;

/**
 *
 * @author s115869
 */
public class Triangle {
    
    Vertex[] v;
    Edge[] e;
    Vertex v1;
    Vertex v2;
    Vertex v3;
    
    public Triangle(Vertex v1, Vertex v2, Vertex v3){
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }
    
    double sign (Vertex p1, Vertex p2, Vertex p3)
{
    return (p1.getx() - p3.getx()) * (p2.gety() - p3.gety()) - (p2.getx() - p3.getx()) * (p1.gety() - p3.gety());
}

boolean PointInTriangle (Vertex pt)
{
    boolean b1, b2, b3;

    b1 = sign(pt, this.v1, this.v2) < 0;
    b2 = sign(pt, this.v2, this.v3) < 0;
    b3 = sign(pt, this.v3, this.v1) < 0;

    return ((b1 == b2) && (b2 == b3));
}

boolean PointOnTriangle (Vertex pt)
{
    boolean b1, b2, b3;

    b1 = sign(pt, this.v1, this.v2) < 0;
    b2 = sign(pt, this.v2, this.v3) < 0;
    b3 = sign(pt, this.v3, this.v1) < 0;

    return ((b1 == b2) && (b2 == b3));
}
}
