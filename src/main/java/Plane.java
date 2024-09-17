public class Plane {

    private BoundingBox planeBbox;

    //plane equation = ax + by + cz + d = 0
    private double aCoefficient;
    private double bCoefficient;
    private final double  cCoefficient = -1.0;
    private double dCoefficient;

    public Plane (double[] coefficients, BoundingBox planeBbox){
        this.aCoefficient = coefficients[0];
        this.bCoefficient = coefficients[1];
        this.dCoefficient = coefficients[2];
        this.planeBbox = planeBbox;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "planeBbox=" + planeBbox +
                ", aCoefficient=" + aCoefficient +
                ", bCoefficient=" + bCoefficient +
                ", cCoefficient=" + cCoefficient +
                ", dCoefficient=" + dCoefficient +
                '}';
    }
}
