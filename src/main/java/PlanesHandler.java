import java.util.*;
import java.util.concurrent.*;

import org.apache.commons.math3.linear.*;

public class PlanesHandler {
    private HashMap<String, Plane> planes;
    private ConcurrentHashMap<String, Double> errors;
    public PlanesHandler() {
        this.planes = new HashMap<>();
        this.errors = new ConcurrentHashMap<>();
    }


    public void generatePlanes(HashMap<String, ArrayList<MyPoint>> roofPointsMap) {
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
    public void getAverageErrors(ArrayList<MyPoint> lasPoints){
        System.out.println("Beginning average errors");
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Void>> futures = new ArrayList<>();
//        this.planes.forEach((key, value) -> {
//                Callable<Void> task = () -> {
//                    filterCoords(key, value, lasPoints);
//                    return null;
//            };
//            futures.add(executor.submit(task));
//        });
//        for (Future<Void> future : futures) {
//            try {
//                future.get();
//
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        executor.shutdown();
        this.planes.forEach((key, plane) -> filterCoords(key, plane, lasPoints));
    }
    private void filterCoords(String key, Plane plane, ArrayList<MyPoint> lasPoints){
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
        OptionalDouble average = distances.stream().mapToDouble(Double::doubleValue).average();
        double error = average.isPresent() ? average.getAsDouble() : 0.0;
        this.errors.put(key, error);
    }

    public ConcurrentHashMap<String, Double> getErrors() {
        return errors;
    }
}
