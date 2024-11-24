package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;

public class AnimatedGradientPanel extends JPanel {
    private float gradientOffset = 0; // Offset for animation
    private boolean forward = true;  // Controls the direction of the gradient

    private final Timer timer = new Timer(30, e -> {
        // Move the gradient offset in the current direction
        if (forward) {
            gradientOffset += 0.008f;
            if (gradientOffset >= 1) {
                gradientOffset = 1;
                forward = false; // Reverse direction
            }
        } else {
            gradientOffset -= 0.008f;
            if (gradientOffset <= 0) {
                gradientOffset = 0;
                forward = true; // Reverse direction
            }
        }
        repaint();
    });

    public AnimatedGradientPanel() {
        timer.start(); // Start the animation
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Calculate animated start and end points for the gradient
        int x1 = (int) (width * gradientOffset);
        int y1 = (int) (height * gradientOffset);
        int x2 = width - x1;
        int y2 = height - y1;

        // Gradient Colors (Pink to Purple)
        Color color1 = new Color(252, 114, 166, 255); // Pink
        Color color2 = new Color(63, 64, 192, 255);   // Purple

        // Create a diagonal gradient paint
        GradientPaint gradientPaint = new GradientPaint(x1, y1, color1, x2, y2, color2);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);
    }
}
