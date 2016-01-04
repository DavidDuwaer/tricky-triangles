package Graph;

/**
 *
 * @author s115869
 */
public class Vertex {
    
    private double x, y;
    
    public Vertex(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double[] getPos()
    {
        return new double[] {x, y};
    }
}
