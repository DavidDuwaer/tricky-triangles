package Graph;

/**
 *
 * @author s115869
 */
public class Triangle {

    Vertex[] v = new Vertex[3];
    Edge[] e;

    public Triangle(Vertex v1, Vertex v2, Vertex v3) {
        this.v[0] = v1;
        this.v[1] = v2;
        this.v[2] = v3;
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

   

    boolean contains(Vertex v1, Vertex v2, Vertex v3) {
        int id1 = v1.getID();
        int id2 = v2.getID();
        int id3 = v3.getID();
        for (Vertex v4 : v) {
            int vd = v4.getID();
            if (vd != id1 && vd != id2 && vd != id3) {
                return false;
            }
        }
        return true;
    }

}
