package frameworks_driver.view.Portfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeButton extends JButton {
    public HomeButton() {
        super("Home");
        setBackground(new Color(52, 152, 219));
        setForeground(Color.BLACK);
        setPreferredSize(new Dimension(80, 30));
    }

    public void addHomeButtonListener(ActionListener listener) {
        addActionListener(listener);
    }
}
