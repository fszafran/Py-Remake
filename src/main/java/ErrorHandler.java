//import java.util.*;
//
//public class ErrorHandler {
//
//    private HashMap<String, Double> errors = new HashMap<>();
//
//    public void getAverageErrors(ArrayList<MyPoint> lasPoints, HashMap<String, Plane> planes){
//        System.out.println("Beginning average errors");
//        planes.forEach((key, plane) -> filterCoords(key, plane, lasPoints));
//    }
//
//    private void filterCoords(String key, Plane plane, ArrayList<MyPoint> lasPoints){
//        BoundingBox planeBbox = plane.getPlaneBbox();
//        double a = plane.getaCoefficient();
//        double b = plane.getbCoefficient();
//        double c = plane.getcCoefficient();
//        double d = plane.getdCoefficient();
//        List<Double> distances = new ArrayList<>();
//        for(MyPoint point : lasPoints){
//            if(planeBbox.containsPoint(point)){
//                double distance = (a * point.getX() + b * point.getY() + c * point.getZ() + d) / (Math.sqrt(a*a + b*b + c*c));
//                if(distance > -1 && distance < 1){
//                    distances.add(distance);
//                }
//            }
//        }
//        double averageError = distances.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
//        this.errors.put(key, averageError);
//    }
//
//    public HashMap<String, Double> getErrors() {
//        return errors;
//    }
//
//    public double getMaxError(){
//        return Collections.max(errors.values());
//    }
//
//    public double getMinError(){
//        return Collections.min(errors.values());
//    }
//}
