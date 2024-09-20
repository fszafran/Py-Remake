public class Plane {

    private BoundingBox planeBbox;

    //plane equation = ax + by + cz + d = 0
    private double aCoefficient;
    private double bCoefficient;
    private final double  cCoefficient = -1.0;
    private double dCoefficient;
    private double error;

    public Plane (double[] coefficients, BoundingBox planeBbox){
        this.aCoefficient = coefficients[1];
        this.bCoefficient = coefficients[2];
        this.dCoefficient = coefficients[0];
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

    public BoundingBox getPlaneBbox() {
        return planeBbox;
    }

    public double getaCoefficient() {
        return aCoefficient;
    }

    public double getbCoefficient() {
        return bCoefficient;
    }

    public double getcCoefficient() {
        return cCoefficient;
    }

    public double getdCoefficient() {
        return dCoefficient;
    }

    public void setError(double error) {
        this.error = error;
    }
    public double getError() {
        return error;
    }
}
