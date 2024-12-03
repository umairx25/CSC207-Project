package frameworks_driver.view.login;

import frameworks_driver.view.style_helpers.ColourManager;
import frameworks_driver.view.style_helpers.FontManager;
import frameworks_driver.view.style_helpers.GridBagManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * Provides utility methods for creating and styling UI components in the login view.
 */
public class UIHelper {

    public static final Border NORMAL_BORDER = new LineBorder(ColourManager.DARK_GRAY, 2);
    public static final Border FOCUSED_BORDER = new LineBorder(ColourManager.DARK_GRAY, 4);

    /**
     * Adds a heading label to a panel.
     *
     * @param panel     the panel to add the heading to
     * @param labelText the text for the heading
     */
    public static void addHeading(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        GridBagConstraints gbc = GridBagManager.headingGBC();
        label.setFont(FontManager.SEGOE_BOLD_FONT_24);
        label.setForeground(ColourManager.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, gbc);
    }

    /**
     * Adds a labeled input field to a panel.
     *
     * @param panel     the panel to add the labeled field to
     * @param labelText the label text
     * @param textField the text field to add
     */
    public static void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setForeground(ColourManager.WHITE);
        label.setFont(FontManager.SEGOE_FONT_14);
        panel.add(label, GridBagManager.labelGBC());

        textField.setFont(FontManager.SEGOE_FONT_12);
        textField.setBorder(NORMAL_BORDER);
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 30));
        addFocusListener(textField);

        panel.add(textField, GridBagManager.inputFieldGBC());
    }

    /**
     * Adds focus listeners to a text field for changing borders.
     *
     * @param textField the text field to add listeners to
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
     * @param button the button to style
     */
    public static void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 40));
        button.setBackground(ColourManager.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(true);
        applyButtonStyle(button);
    }

    /**
     * Applies hover and click styles to a button.
     *
     * @param button the button to apply styles to
     */
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
    /**
     * Displays an error message on the specified JPanel. If an error message label
     * with the same color already exists, it updates the text. Otherwise, it creates
     * a new error label and positions it based on the provided section.
     *
     * @param panel   the JPanel to display the error message on
     * @param message the error message to display
     * @param section the section identifier ("login" or "signup") to determine positioning
     */
    public static void showErrorMessage(JPanel panel, String message, String section) {
        // Check if an error message label already exists in the panel
        JLabel errorMessageLabel = null;
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel && ColourManager.ERROR_RED.equals(((JLabel) component).getForeground())) {
                errorMessageLabel = (JLabel) component;
                break;
            }
        }

        // If no existing error message label, create one
        if (errorMessageLabel == null) {
            errorMessageLabel = new JLabel();
            errorMessageLabel.setForeground(ColourManager.ERROR_RED);
            errorMessageLabel.setFont(FontManager.ITALIC_SEGOE_FONT_10);

            // Set positioning constraints for the error label based on the section
            GridBagConstraints gbc = Objects.equals(section, "login")
                    ? GridBagManager.errorMsgGBC(5, 120)
                    : GridBagManager.errorMsgGBC(11, 30);

            panel.add(errorMessageLabel, gbc);
        }

        // Update the text of the error message
        errorMessageLabel.setText(message);

        // Ensure the error message is visible and update the panel
        errorMessageLabel.setVisible(true);
        panel.revalidate();
        panel.repaint();
    }

}
