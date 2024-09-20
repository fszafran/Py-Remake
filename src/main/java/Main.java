import com.github.mreutegg.laszip4j.LASReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class Main {

    public static void main(String[] args) {
        float startTime = System.nanoTime();
        File lasFile = new File("C:\\Users\\filo1\\Desktop\\stare\\pr\\las\\78776_1433575_M-34-47-D-c-1-3-3-1.laz");
        File gmlDirectory = new File("C:\\Users\\filo1\\Desktop\\stare\\pr\\gmls\\Modele_3D");

        LASReader lasReader = new LASReader(lasFile);
        LasPointsHandler lasPointsHandler = new LasPointsHandler();
        lasPointsHandler.readLasPoints(lasReader);
        ArrayList<MyPoint> lasPoints = lasPointsHandler.getLasPoints();
        BoundingBox globalBbox = lasPointsHandler.getGlobalBbox();
        RoofPointsHolder roofPointsHolder = new RoofPointsHolder();
        roofPointsHolder.generateRoofPointsFromDirectory(gmlDirectory, globalBbox);
        PlanesHandler planesHandler = new PlanesHandler();
        planesHandler.generatePlanes(roofPointsHolder.getRoofPointsMap(), lasPoints);
        float endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) * 1e-9;
        System.out.println("Execution time: " + elapsedTime + " seconds");
    }
}

