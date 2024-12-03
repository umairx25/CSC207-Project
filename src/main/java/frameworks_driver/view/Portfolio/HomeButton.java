package frameworks_driver.view.Portfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import frameworks_driver.view.style_helpers.ColourManager;


public class HomeButton extends JButton {
    public HomeButton() {
        super("Home");
        setBackground(ColourManager.MEDIUM_GRAY);
        setPreferredSize(new Dimension(80, 30));
    }

    public void addHomeButtonListener(ActionListener listener) {
        addActionListener(listener);
    }
}
