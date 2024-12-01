package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverEffectUtility {

    private static final Color HOVER_COLOR = new Color(220, 220, 220); // Light gray
    private static final Color DEFAULT_COLOR = Color.WHITE;           // White

    public static void applyHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(HOVER_COLOR); // Change background to light gray on hover
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(DEFAULT_COLOR); // Revert to white on exit
            }
        });
    }
}
