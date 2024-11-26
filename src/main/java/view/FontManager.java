package view;

import java.awt.Font;
import java.io.File;

public class FontManager {
    // Define all commonly used fonts as constants
    public static final Font SEGOE_FONT_16 = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font SEGOE_FONT_14 = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SEGOE_FONT_12 = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font SEGOE_FONT_10 = new Font("Segoe UI", Font.PLAIN, 10);
    public static final Font ITALIC_SEGOE_FONT_10 = new Font("Segoe UI", Font.ITALIC, 10);
    public static final Font SEGOE_BOLD_FONT_24 = new Font("Segoe UI", Font.BOLD, 24);

    public static final Font OUTFIT_REGULAR_10 = loadFont("assets/Outfit-Regular.ttf", Font.PLAIN, 10);
    public static final Font OUTFIT_REGULAR_12 = loadFont("assets/Outfit-Regular.ttf", Font.PLAIN, 12);
    public static final Font OUTFIT_BOLD_16 = loadFont("assets/Outfit-Regular.ttf", Font.BOLD, 16);

    private FontManager() {}

    private static Font loadFont(String filePath, int style, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(filePath)).deriveFont(style, size);
        } catch (Exception e) {
            // Fall back to a default font if loading fails
            return new Font("SansSerif", style, size);
        }
    }
}