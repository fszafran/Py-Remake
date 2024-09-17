public class Plane {

    private BoundingBox planeBbox;

    //plane equation = ax + by + cz + d = 0
    private double aCoefficient;
    private double bCoefficient;
    private final double  cCoefficient = -1.0;
    private double dCoefficient;

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

    public void setPlaneBbox(BoundingBox planeBbox) {
        this.planeBbox = planeBbox;
    }

    public double getaCoefficient() {
        return aCoefficient;
    }

    public void setaCoefficient(double aCoefficient) {
        this.aCoefficient = aCoefficient;
    }

    public double getbCoefficient() {
        return bCoefficient;
    }

    public void setbCoefficient(double bCoefficient) {
        this.bCoefficient = bCoefficient;
    }

    public double getcCoefficient() {
        return cCoefficient;
    }

    public double getdCoefficient() {
        return dCoefficient;
    }

    public void setdCoefficient(double dCoefficient) {
        this.dCoefficient = dCoefficient;
    }
}
