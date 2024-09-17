public class BoundingBox {

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public BoundingBox(){
        this.minX = Double.MAX_VALUE;
        this.maxX = Double.MIN_VALUE;
        this.minY = Double.MAX_VALUE;
        this.maxY = Double.MIN_VALUE;
    }

    public BoundingBox(double minX, double minY, double maxX, double maxY){
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public boolean containsBbox(BoundingBox other){
        return !(this.maxX < other.minX) && !(this.minX > other.maxX) && !(this.maxY < other.minY) && !(this.minY > other.maxY);
    }

    public boolean containsPoint(float x, float y){
        return !(this.maxX < x) && !(this.minX > x) && !(this.maxY < y) && !(this.minY > y);
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMinY() {
        return this.minY;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMaxY() {
        return this.maxY;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    @Override
    public String toString() {
        return "BoundingBox{" +
                "minX=" + minX +
                ", minY=" + minY +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                '}';
    }
}
