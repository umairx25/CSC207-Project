package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GridBagManager {

    // Preferred sizes
    public static final Dimension HEADER_SIZE = new Dimension(500, 80);
    public static final Dimension INPUT_SIZE = new Dimension(500, 50);
    public static final Dimension SEND_MSG_SIZE = new Dimension(55, 55);

    // Insets for different components
    public static final Insets ICON_INSETS = new Insets(15, 15, 15, 10);
    public static final Insets NAME_INSETS = new Insets(0, 5, 0, 0);
    public static final Insets TYPING_INSETS = new Insets(10, 0, 0, 0);
    public static final Insets PANEL_INSETS = new Insets(0, 10, 0, 10);

    // Borders
    public static final Border ICON_BORDER = new EmptyBorder(15, 15, 15, 10);
    public static final Border INPUT_BORDER = new EmptyBorder(10, 10, 10, 10);
    public static final Border TYPING_BORDER = new EmptyBorder(8, 0, 0, 0);
    public static final Border TIME_STAMP_BORDER = new EmptyBorder(5, 0, 0, 0);

    public static final FlowLayout HEADER_FLOW_LAYOUT = new FlowLayout(FlowLayout.LEFT, 10, 30); // FlowLayout for header

    // Predefined GridBagConstraints
    public static final GridBagConstraints ICON_GBC = createGBC(0, 0, ICON_INSETS, GridBagConstraints.WEST, GridBagConstraints.NONE);
    public static final GridBagConstraints NAME_GBC = createGBC(0, 0, NAME_INSETS, GridBagConstraints.WEST, GridBagConstraints.NONE);
    public static final GridBagConstraints TYPING_GBC = createGBC(0, 1, TYPING_INSETS, GridBagConstraints.WEST, GridBagConstraints.NONE);
    public static final GridBagConstraints PANEL_GBC = createGBC(1, 0, PANEL_INSETS, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);


    public static GridBagConstraints createGBC(int gridx, int gridy, Insets insets, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = insets;
        gbc.anchor = anchor;
        gbc.fill = fill;
        return gbc;
    }



    public static GridBagConstraints headingGBC() {
        return createGBC(0, GridBagConstraints.RELATIVE, new Insets(20, 10, 20, 10), GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
    }
    public static GridBagConstraints labelGBC() {
        return createGBC(0, GridBagConstraints.RELATIVE, new Insets(5, 10, 2, 10), GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
    }
    public static GridBagConstraints errorMsgGBC(int gridy, int left) {
        return createGBC(0, gridy, new Insets(5, left, 0, 0), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }
    public static GridBagConstraints rememberGBC() {
        return createGBC(0, GridBagConstraints.RELATIVE, new Insets(15, 10, 10, 10), GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
    }
    public static GridBagConstraints inputFieldGBC() {
        return createGBC(0, GridBagConstraints.RELATIVE, new Insets(2, 10, 5, 10), GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
    }
    public static GridBagConstraints loginButtonGBC() {
        return createGBC(0, GridBagConstraints.RELATIVE, new Insets(15, 10, 10, 10), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }
    public static GridBagConstraints signUpButtonsGBC() {
        return createGBC(0, GridBagConstraints.RELATIVE, new Insets(20, 10, 10, 10), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }
    public static GridBagConstraints signUpLabelGBC() {
        return createGBC(0, GridBagConstraints.RELATIVE, new Insets(5, 35, 10, 10), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }
}
