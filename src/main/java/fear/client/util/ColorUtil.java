package fear.client.util;
import java.awt.Color;
public class ColorUtil {
    public static int rgb(int r, int g, int b, int a) { return new Color(r, g, b, a).getRGB(); }
    public static int rainbow(int speed) { return Color.HSBtoRGB((System.currentTimeMillis() % (speed * 1000)) / (float)(speed * 1000), 1, 1); }
}