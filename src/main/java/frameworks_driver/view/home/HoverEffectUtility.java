package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Utility class for applying hover effects to buttons.
 */
public class HoverEffectUtility {

    private static final Color HOVER_COLOR = new Color(220, 220, 220);
    private static final Color DEFAULT_COLOR = Color.WHITE;

    /**
     * Applies a hover effect to the given button.
     *
     * @param button the button to apply the effect to
     */
    public static void applyHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(DEFAULT_COLOR);
            }
        });
    }
}
