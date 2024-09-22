import com.github.mreutegg.laszip4j.LASReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DataProcessor {
    private File lasFile;
    private File directoryWithGmlFiles;

    public DataProcessor(File lasFile, File directoryWithGmlFiles) {
        this.lasFile = lasFile;
        this.directoryWithGmlFiles = directoryWithGmlFiles;
    }
    public void process() {
        LASReader lasReader = new LASReader(this.lasFile);

        LasPointsHandler lasPointsHandler = new LasPointsHandler();
        lasPointsHandler.readLasPoints(lasReader);
        ArrayList<MyPoint> lasPoints = lasPointsHandler.getLasPoints();
        BoundingBox globalBbox = lasPointsHandler.getGlobalBbox();

        RoofPointsHandler roofPointsHolder = new RoofPointsHandler();
        HashMap<String, ArrayList<MyPoint>> roofPoints = roofPointsHolder.generateRoofPointsFromDirectory(this.directoryWithGmlFiles, globalBbox);

        PlanesHandler planesHandler = new PlanesHandler();
        HashMap<String, Plane> planes = planesHandler.generatePlanes(roofPoints, lasPoints);
    }
}
