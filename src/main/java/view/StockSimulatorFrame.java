// src/ui/StockSimulatorFrame.java
package view;

import javax.swing.*;
import java.awt.*;

public class StockSimulatorFrame extends JFrame {

    public StockSimulatorFrame() {
        // Set frame title
        setTitle("Stock Sim");

        // Set frame size
        setSize(800, 600);

        // Center the frame
        setLocationRelativeTo(null);

        // Close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color and add label
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Stock Sim");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label);

        // Add panel to frame
        add(panel);

        // Set frame visibility
        setVisible(true);
    }
}