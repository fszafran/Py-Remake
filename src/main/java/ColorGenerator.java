import javafx.scene.paint.Color;

public class ColorGenerator {

    private static Color assignColorToValue(double value, double minValue, double maxValue) {
    if(value < minValue){
        value = minValue;
    }
    if(value > maxValue){
        value = maxValue;
    }
    double hue = 60 - ((value - minValue) / (maxValue - minValue) * 60);
    return Color.hsb(hue, 1.0, 1.0);
}

    public static Color[] getColors(double[] values){
        Color[] colors = new Color[values.length];
        double min = values[0];
        double max = values[values.length-1];
        for(int i = 0; i < values.length; i++){
            colors[i] = assignColorToValue(values[i], min, max);
        }
        return colors;
    }

    public static Color getPLaneColor(Color[] colors, double[] errors, double planeError){
        Color planeColor = Color.BLACK;
        for(int i = 0; planeError > errors[i]; i++){
            planeColor = colors[i];
        }
        return planeColor;
    }


}
