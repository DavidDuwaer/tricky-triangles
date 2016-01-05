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
    
    public static double cross(Vertex A, Vertex B, Vertex O){
        return (A.x - O.x)*(B.y-O.y) - (A.y - O.y)*(B.x-O.x);
    }
    
    public void swap_point(Vertex A, Vertex B) {
        Vertex temp = new Vertex(A.x, A.y, A.getID());
        A.x = B.x; 
        A.y = B.y; 
        B.x = temp.x; 
        B.y = temp.y;
    }
    
    public static boolean less_than(Vertex A, Vertex B){
        if(A.x<B.x || (A.x == B.x && A.y< B.y)){
            return true;
        }
        return false;
    }
    
    public int compareTo(Vertex o){
        if (Vertex.less_than(this,o)){
            return -1;
        } else if (Vertex.less_than(o,this)){
            return 1;
        } else {
            return 0;
        }
    }
    
}
