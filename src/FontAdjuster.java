import java.awt.*;

public class FontAdjuster {
    public static Font increaseFont(Font font, int style, int factor) {
        return new Font(font.getName(), style, font.getSize() * factor);
    }
}