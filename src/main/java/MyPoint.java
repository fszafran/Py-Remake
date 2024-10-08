public class MyPoint {
    private double x;
    private double y;
    private double z;

    public MyPoint(double x, double y, double z) {
        this(x,y);
        this.z = z;
    }
    public MyPoint(double x, double y){
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }
}
