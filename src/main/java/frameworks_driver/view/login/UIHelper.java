package frameworks_driver.view.login;

import view.ColourManager;
import view.FontManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class UIHelper {
    public static final Border NORMAL_BORDER = new LineBorder(ColourManager.DARK_GRAY, 2);
    public static final Border FOCUSED_BORDER = new LineBorder(ColourManager.DARK_GRAY, 4);

    /**
     * Adds a heading label to a panel.
     *
     * @param panel The panel to add the heading to.
     * @param labelText The text for the heading.
     */
    public static void addHeading(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        GridBagConstraints gbc = GridBagHelper.headingGBC();
        gbc.gridwidth = 2;

        label.setFont(FontManager.SEGOE_BOLD_FONT_24);
        label.setForeground(ColourManager.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, gbc);
    }

    /**
     * Adds a labeled input field to a panel.
     *
     * @param panel The panel to add the labeled field to.
     * @param labelText The label text.
     * @param textField The text field to add.
     */
    public static void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        GridBagConstraints gbcLabel = GridBagHelper.labelGBC();
        JLabel label = new JLabel(labelText);
        label.setForeground(ColourManager.WHITE);
        label.setFont(FontManager.SEGOE_FONT_14);
        panel.add(label, gbcLabel);

        textField.setFont(FontManager.SEGOE_FONT_12);
        textField.setBorder(NORMAL_BORDER);
        textField.setMargin(new Insets(0, 10, 0, 0));
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 30));

        addFocusListener(textField);
        GridBagConstraints gbcField = GridBagHelper.inputFieldGBC();
        panel.add(textField, gbcField);
    }

    /**
     * Adds focus listeners to a text field for changing borders.
     *
     * @param textField The text field to add listeners to.
     */
    private static void addFocusListener(JTextField textField) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(FOCUSED_BORDER);
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(NORMAL_BORDER);
            }
        });
    }

    /**
     * Styles a button.
     *
     * @param button The button to style.
     */
    public static void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 40));
        button.setBackground(ColourManager.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(true);

        applyButtonStyle(button);
    }

    private static void applyButtonStyle(JButton button) {
        button.setBorder(new LineBorder(ColourManager.DARK_GRAY, 4));
        button.setBackground(ColourManager.WHITE);
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBorder(new LineBorder(ColourManager.DARK_GRAY, 5));
                button.setBackground(ColourManager.LIGHT_GRAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBorder(new LineBorder(ColourManager.DARK_GRAY, 4));
                button.setBackground(ColourManager.WHITE);
            }
        });
    }

    public static void showErrorMessage(JPanel panel, String message, String section) {
        // Create or reuse a JLabel for the error message
        JLabel errorMessageLabel = new JLabel(message);
        errorMessageLabel.setForeground(ColourManager.ERROR_RED);
        errorMessageLabel.setFont(FontManager.ITALIC_SEGOE_FONT_10);

        // Determine GridBagConstraints for the error message based on the section
        GridBagConstraints gbc;
        if (Objects.equals(section, "login")) {
            gbc = GridBagHelper.errorMsgGBC(5, 120); // Example positioning for login error
        } else {
            gbc = GridBagHelper.errorMsgGBC(11, 30); // Example positioning for signup error
        }

        // Add the error message to the panel if it's not already added
        if (panel.getComponentZOrder(errorMessageLabel) == -1) {
            panel.add(errorMessageLabel, gbc);
        }

        // Make the error message visible and update the panel layout
        errorMessageLabel.setVisible(true);
        panel.revalidate();
        panel.repaint();
    }



}
