package frameworks_driver.view.Portfolio;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class QuantityTextField extends JFormattedTextField {
    public QuantityTextField() {
        super(createFormatter());
        setPreferredSize(new Dimension(100, 30));
        setBorder(BorderFactory.createTitledBorder("Quantity"));
        setValue(0);
    }

    private static NumberFormatter createFormatter() {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setAllowsInvalid(false);
        return formatter;
    }

    public Object getValue() {
        Object value = super.getValue();
        return value == null ? 0 : (Integer) value;
    }
}
