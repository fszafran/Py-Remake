import java.util.*;

import org.apache.commons.math3.linear.*;

public class PlanesHandler {

    public HashMap<String, Plane> generatePlanes(HashMap<String, ArrayList<MyPoint>> roofPointsMap, ArrayList<MyPoint> lasPoints) {
        var startTime = System.nanoTime();
        HashMap<String, Plane> planes = new HashMap<>();

            roofPointsMap.forEach((key, value) -> {
                double[] xArray = new double[value.size()];
                double[] yArray = new double[value.size()];
                double[] zArray = new double[value.size()];
                for (int i = 0; i < value.size(); i++) {
                    xArray[i] = value.get(i).getX();
                    yArray[i] = value.get(i).getY();
                    zArray[i] = value.get(i).getZ();
                }
                double minX = Arrays.stream(xArray).min().getAsDouble();
                double maxX = Arrays.stream(xArray).max().getAsDouble();
                double minY = Arrays.stream(yArray).min().getAsDouble();
                double maxY = Arrays.stream(yArray).max().getAsDouble();
                BoundingBox planeBbox = new BoundingBox(minX, minY, maxX, maxY);

                RealMatrix coefficients = getPlaneCoefficients(xArray, yArray, zArray);
                Plane plane = new Plane(coefficients.getColumn(0), planeBbox); // always 1 column 3 rows
                plane.setError(filterCoords(plane, lasPoints));
                planes.put(key, plane);
        });
            var endTime = System.nanoTime();
            System.out.println("Planes: " + (endTime - startTime) / 1000000 + "s");
            return planes;
    }

    private RealMatrix getPlaneCoefficients(double[] xArray, double[] yArray, double[] zArray){
        double[] onesRow = new double[xArray.length];
        Arrays.fill(onesRow, 1.0);
        RealMatrix A = MatrixUtils.createRealMatrix(new double[][]{
                onesRow,
                xArray,
                yArray
        }).transpose();
        RealMatrix AT = A.transpose();
        RealMatrix B = MatrixUtils.createColumnRealMatrix(zArray);
        RealMatrix ATA = AT.multiply(A);
        RealMatrix ATAInv = new LUDecomposition(ATA).getSolver().getInverse();
        return ATAInv.multiply(AT).multiply(B);
    }
    private double filterCoords(Plane plane, ArrayList<MyPoint> lasPoints){
        BoundingBox planeBbox = plane.getPlaneBbox();
        double a = plane.getaCoefficient();
        double b = plane.getbCoefficient();
        double c = plane.getcCoefficient();
        double d = plane.getdCoefficient();
        List<Double> distances = new ArrayList<>();
        for(MyPoint point : lasPoints){
            if(planeBbox.containsPoint(point)){
                double distance = (a * point.getX() + b * point.getY() + c * point.getZ() + d) / (Math.sqrt(a*a + b*b + c*c));
                if(distance > -1 && distance < 1){
                    distances.add(distance);
                }
            }
        }
        return distances.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

}
