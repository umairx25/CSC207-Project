package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private static final String IMAGE_PATH = "images/";
    private static final Map<String, ImageIcon> imageCache = new HashMap<>();

    // Static block to preload images
    static {
        preloadImages();
    }

    private static void preloadImages() {
        // Add all the images here with their respective paths and sizes
        imageCache.put("logo", getScaledImageIcon("stock_flow_logo.png", 400, 400));
        imageCache.put("icon_small", getScaledImageIcon("icon.png", 100, 100));
        // Add more images as needed
    }

    private static ImageIcon getScaledImageIcon(String imageName, int width, int height) {
        ImageIcon icon = new ImageIcon(IMAGE_PATH + imageName);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static ImageIcon getImage(String key) {
        return imageCache.get(key);
    }
}
