package frameworks_driver.view.style_helpers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages and provides access to preloaded images with scaling functionality.
 */
public class ImageManager {

    private static final Map<String, ImageIcon> imageCache = new HashMap<>();

    // Static block to preload images
    static {
        preloadImages();
    }

    /**
     * Preloads all required images and caches them in memory.
     */
    private static void preloadImages() {
        imageCache.put("logo", getScaledImageIcon("assets/stock_flow_logo.png", 400, 400));
        imageCache.put("chatbot_pfp", getScaledImageIcon("assets/chatbot_icon.png", 50, 50));
        imageCache.put("send_icon", getScaledImageIcon("assets/sendMessageImg.png", 60, 60));
    }

    /**
     * Retrieves a scaled version of the specified image.
     *
     * @param imageName the path of the image file
     * @param width     the desired width of the image
     * @param height    the desired height of the image
     * @return a scaled ImageIcon
     */
    private static ImageIcon getScaledImageIcon(String imageName, int width, int height) {
        ImageIcon icon = new ImageIcon(imageName);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Gets an image from the cache by its key.
     *
     * @param key the key associated with the image
     * @return the ImageIcon, or null if the key does not exist
     */
    public static ImageIcon getImage(String key) {
        return imageCache.get(key);
    }
}
