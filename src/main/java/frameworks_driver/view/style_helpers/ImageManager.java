package frameworks_driver.view.style_helpers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private static final Map<String, ImageIcon> imageCache = new HashMap<>();

    // Static block to preload images
    static {
        preloadImages();
    }

    private static void preloadImages() {
        // Add all the images here with their respective paths and sizes
        imageCache.put("logo", getScaledImageIcon("assets/stock flow_logo.png", 400, 400));
        imageCache.put("chatbot_pfp", getScaledImageIcon("assets/chatbot_icon.png", 50, 50));
        imageCache.put("send_icon", getScaledImageIcon("assets/sendMessageImg.png", 60, 60));
    }

    private static ImageIcon getScaledImageIcon(String imageName, int width, int height) {
        ImageIcon icon = new ImageIcon(imageName);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static ImageIcon getImage(String key) {
        return imageCache.get(key);
    }
}
