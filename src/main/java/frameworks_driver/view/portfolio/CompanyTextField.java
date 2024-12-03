package frameworks_driver.view.portfolio;

import javax.swing.*;
import java.awt.*;

public class CompanyTextField extends JTextField {
    public CompanyTextField() {
        setPreferredSize(new Dimension(150, 30));
        setToolTipText("Enter company ticker");
        setBorder(BorderFactory.createTitledBorder("Company"));
    }

    @Override
    public String getText() {
        return super.getText().toUpperCase().trim();
    }
}
