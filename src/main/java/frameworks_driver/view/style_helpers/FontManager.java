package frameworks_driver.view.style_helpers;

import java.awt.Font;
import java.io.File;

/**
 * Provides access to commonly used fonts in the application.
 */
public class FontManager {
    public static final Font SEGOE_FONT_14 = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SEGOE_FONT_12 = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font SEGOE_FONT_10 = new Font("Segoe UI", Font.PLAIN, 10);
    public static final Font ITALIC_SEGOE_FONT_10 = new Font("Segoe UI", Font.ITALIC, 10);
    public static final Font ITALIC_SEGOE_FONT_12 = new Font("Segoe UI", Font.ITALIC, 12);

    public static final Font SEGOE_BOLD_FONT_24 = new Font("Segoe UI", Font.BOLD, 24);

    public static final Font OUTFIT_REGULAR_10 = loadFont(Font.PLAIN, 10);
    public static final Font OUTFIT_REGULAR_12 = loadFont(Font.PLAIN, 12);
    public static final Font OUTFIT_BOLD_16 = loadFont(Font.BOLD, 16);
    public static final Font OUTFIT_BOLD_22 = loadFont(Font.BOLD, 22);

    private FontManager() {}

    /**
     * Loads a custom font from the specified file.
     *
     * @param style the font style
     * @param size  the font size
     * @return the loaded font or a default font if loading fails
     */
    private static Font loadFont(int style, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("assets/Outfit-Regular.ttf")).deriveFont(style, size);
        } catch (Exception e) {
            // Fall back to a default font if loading fails
            return new Font("SansSerif", style, size);
        }
    }
}
