import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.commons.math3.linear.*;

public class PlanesHandler {
    HashMap<String, Plane> planes;

    public PlanesHandler() {
        this.planes = new HashMap<>();
    }

    public void generatePlanes(HashMap<String, ArrayList<RoofPoint>> roofPointsMap) {
        var startTime = System.nanoTime();
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

                double[] onesRow = new double[value.size()];
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
                RealMatrix coefficients = ATAInv.multiply(AT).multiply(B);
                Plane plane = new Plane(coefficients.getColumn(0), planeBbox); // always 1 column 3 rows
                this.planes.put(key, plane);
        });
            var endTime = System.nanoTime();
            System.out.println("Planes: " + (endTime - startTime) / 1000000 + "s");
    }
}
