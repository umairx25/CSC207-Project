package view;

import java.awt.*;

public class GridBagManager {

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
