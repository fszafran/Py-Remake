import com.github.mreutegg.laszip4j.LASReader;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Main {

    public static void main(String[] args) {
        float startTime = System.nanoTime();
        File lasFile = new File("stare\\pr\\las\\78776_1433575_M-34-47-D-c-1-3-3-1.laz");
        File gmlDirectory = new File("stare\\pr\\gmls\\Modele_3D");

        DataProcessor dataProcessor = new DataProcessor(lasFile, gmlDirectory);
        dataProcessor.process();

        float endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) * 1e-9;
        System.out.println("Total execution time: " + elapsedTime + " seconds");
    }
}

