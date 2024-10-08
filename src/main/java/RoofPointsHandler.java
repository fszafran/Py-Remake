import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoofPointsHandler {

    public HashMap<String, ArrayList<MyPoint>> generateRoofPointsFromDirectory(File directory, BoundingBox globalBbox) {
        var start = System.nanoTime();
        System.out.println("Extracting Lod2 Points...");
        HashMap<String, ArrayList<MyPoint>>roofPointsMap = new HashMap<>();
        ArrayList<File> filesFittingGlobalBbox = getLOD2FilesFittingTheGlobalBbox(directory, globalBbox);
        for (File file : filesFittingGlobalBbox) {
            RoofParser.loadRoofPointsFromFileIntoMap(file, roofPointsMap, globalBbox);
        }
        var end = System.nanoTime();
        System.out.println("Generating roofPoints took: " + (end - start) * 1e-9 + " s");
        return roofPointsMap;
    }


    public ArrayList<File> getLOD2FilesFittingTheGlobalBbox(File directory, BoundingBox globalBbox){
        int LINELIMIT = 30; // upper & lowerCorner should be in line 15-16 (30 for safety),no need to read whole file

        ArrayList<File> filesFittingGlobalBbox = new ArrayList<>();

        File[] files = directory.listFiles();

        for(File file : files){
            int lineCounter=0;
            if(file.getName().endsWith(".gml")){
                try{
                    List<String> lines = Files.readAllLines(Path.of(file.getPath()));
                    String[] lowerCorner = null;
                    String[] upperCorner = null;

                    for(String line : lines){
                        if(lineCounter == LINELIMIT){
                            break;
                        }
                        if(line.contains("<gml:lowerCorner>")){
                            lowerCorner = line.split(">")[1].split(" ");
                        }
                        else if(line.contains("<gml:upperCorner>")){
                            upperCorner = line.split(">")[1].split(" ");
                        }
                        lineCounter++;
                    }

                    if(lowerCorner == null || upperCorner == null){
                        continue;
                    }
                    double xMin = Float.parseFloat(lowerCorner[0]);
                    double yMin = Float.parseFloat(lowerCorner[1]);
                    double xMax = Float.parseFloat(upperCorner[0]);
                    double yMax = Float.parseFloat(upperCorner[1]);

                    BoundingBox fileBbox = new BoundingBox(xMin, yMin, xMax, yMax);
                    if(globalBbox.containsBbox(fileBbox)){
                        filesFittingGlobalBbox.add(file);
                    }
                }
                catch(IOException e){
                    System.out.println("Error while opening file in getLOD2FilesFittingTheGlobalBbox: " + file.getPath());;
                }
            }
        }
        return filesFittingGlobalBbox;
    }
}
