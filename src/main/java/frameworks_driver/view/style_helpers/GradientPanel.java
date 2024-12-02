package frameworks_driver.view.style_helpers;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a panel with an animated gradient background.
 */
public class GradientPanel extends JPanel {

    private float gradientOffset = 0; // Tracks gradient animation offset
    private boolean forward = true;  // Controls animation direction
    private final Timer timer;       // Timer for animation

    /**
     * Constructs a new GradientPanel and starts the gradient animation.
     */
    public GradientPanel() {
        timer = new Timer(20, e -> {
            if (forward) {
                gradientOffset += 0.008f;
                if (gradientOffset >= 1) {
                    forward = false;
                }
            } else {
                gradientOffset -= 0.008f;
                if (gradientOffset <= 0) {
                    forward = true;
                }
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // Calculate dynamic positions for gradient
        int x1 = (int) (width * gradientOffset);
        int y1 = (int) (height * gradientOffset);
        int x2 = width - x1;
        int y2 = height - y1;

        // Create a gradient
        GradientPaint gradientPaint = new GradientPaint(x1, y1, ColourManager.GRADIENT_COLOR_PINK, x2, y2, ColourManager.GRADIENT_COLOR_BLUE);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);
    }
}
