import com.github.mreutegg.laszip4j.LASPoint;
import com.github.mreutegg.laszip4j.LASReader;

import java.util.ArrayList;
import java.util.Arrays;

public class LasPointsHandler {

    private BoundingBox globalBbox;
    private ArrayList<ArrayList<Double>> lasPoints;
    public LasPointsHandler() {
        this.globalBbox = new BoundingBox();
        this.lasPoints = new ArrayList<>();
    }

    public void readLasPoints(LASReader lasReader){
        System.out.println("Reading laz...");
        var start = System.nanoTime();
        float counter = 0;
        for(LASPoint p : lasReader.getPoints()) {
            if(p.getClassification() == 6){
                counter++;
                double x = (double) p.getX() /100;
                double y = (double) p.getY() /100;
                double z = (double) p.getZ() /100;
                this.lasPoints.add(new ArrayList<>(Arrays.asList(x,y,z)));
                this.globalBbox.setMinX(Math.min(x, globalBbox.getMinX()));
                this.globalBbox.setMaxX(Math.max(x, globalBbox.getMaxX()));
                this.globalBbox.setMinY(Math.min(y, globalBbox.getMinY()));
                this.globalBbox.setMaxY(Math.max(y, globalBbox.getMaxY()));
            }
        }

        var stop = System.nanoTime();
        System.out.println("Extracted: " + counter + " Points in: "+ (stop-start) * 1e-9 + " seconds");

        System.out.println("minX: "+ globalBbox.getMinX() + " maxX: " + globalBbox.getMaxX() + " minY: "
                + globalBbox.getMinY() + " maxY: " + globalBbox.getMaxY());

    }

    public BoundingBox getGlobalBbox(){
        return this.globalBbox;
    }

    public ArrayList<ArrayList<Double>> getLasPoints() {
        return this.lasPoints;
    }
}
