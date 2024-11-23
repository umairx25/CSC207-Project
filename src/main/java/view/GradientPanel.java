package view;

import view.ColourManager;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private float gradientOffset = 0; // Tracks gradient animation offset
    private boolean forward = true;  // Controls animation direction
    private final Timer timer;       // Timer for animation

    public GradientPanel() {
        // Initialize the animation Timer
        timer = new Timer(20, e -> {
            if (forward) {
                gradientOffset += 0.008f;
                if (gradientOffset >= 1) {
                    gradientOffset = 1;
                    forward = false;
                }
            } else {
                gradientOffset -= 0.008f;
                if (gradientOffset <= 0) {
                    gradientOffset = 0;
                    forward = true;
                }
            }
            repaint();
        });
        timer.start(); // Start the animation
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast Graphics to Graphics2D for advanced painting
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // Calculate dynamic positions for gradient based on offset
        int x1 = (int) (width * gradientOffset);
        int y1 = (int) (height * gradientOffset);
        int x2 = width - x1;
        int y2 = height - y1;

        // Define the gradient with two colors
        GradientPaint gradientPaint = new GradientPaint(x1, y1, ColourManager.GRADIENT_COLOR_PINK,
                x2, y2, ColourManager.GRADIENT_COLOR_BLUE);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height); // Fill the panel with the gradient
    }
}
