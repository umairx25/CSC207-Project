package frameworks_driver.view.portfolio;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class QuantityTextField extends JFormattedTextField {
    public QuantityTextField() {
        super();
        setPreferredSize(new Dimension(100, 30));
        setBorder(BorderFactory.createTitledBorder("Quantity"));
        setValue(0);
    }

    public Object getValue() {
        Object value = super.getValue();
        return value == null ? 0 : (Integer) value;
    }
}
