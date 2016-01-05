package Graph;

/**
 *
 * @author s115869
 */
public class Vertex {
    
    private double x, y;
    private int id;
    
    public Vertex(double x, double y, int id)
    {
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    public double[] getPos()
    {
        return new double[] {x, y};
    }
    
    public int getID(){
        return id;
    }
}
