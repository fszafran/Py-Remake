import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RoofParser {

    public static void loadRoofPointsFromFileIntoMap(File file, HashMap<String,ArrayList<MyPoint>> pointsMap, BoundingBox globalBbox) {
        int roofCount = 0;
        Document document = parse(file);
        Element rootNode = document.getRootElement();

        for(Element element : rootNode.getChildren()){
            if(element.getName().equals("cityObjectMember")){
                for(Element building : element.getChildren()){
                    if(building.getName().equals("Building")){
                        for(Element bounds : building.getChildren()){
                            if(bounds.getName().equals("boundedBy")){
                                for(Element surface : bounds.getChildren()){
                                    if(surface.getName().equals("RoofSurface")){
                                        String id = surface.getAttributes().getFirst().getValue();
                                        ArrayList<MyPoint> pointsArray = extractRoofPointsFromPos(surface, globalBbox);
                                        if(pointsArray.isEmpty()){
                                            continue; // roof has points outside globalBbox, is skipped
                                        }
                                        pointsMap.put(id, pointsArray);
                                        roofCount++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("RoofCount: " + roofCount);
    }

    public static Document parse(File file) {
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        String path = file.getAbsolutePath();

        try {
            document = builder.build(path);
        }
        catch (JDOMException | IOException e) {
            System.out.println("Error while parsing file: " + e.getMessage());
            e.printStackTrace();

        }

        return document;
    }

    public static ArrayList<MyPoint> extractRoofPointsFromPos(Element roofSurface, BoundingBox globalBbox){
        ArrayList<MyPoint> points = new ArrayList<>();

        //no longer multiple children so we can just get the first one
        Element currentChild = roofSurface.getChildren().getFirst();
        while(!currentChild.getName().equals("LinearRing")){
            currentChild = currentChild.getChildren().getFirst();
        }
        for(Element pos : currentChild.getChildren()){
            String[] coordinates = pos.getValue().split(" ");
            double x = Float.parseFloat(coordinates[0]);
            double y = Float.parseFloat(coordinates[1]);
            double z = Float.parseFloat(coordinates[2]);
            if(!globalBbox.containsPoint(x, y)){
                return new ArrayList<>();
            }
            points.add(new MyPoint(x, y, z));
        }
        return points;
    }


}
