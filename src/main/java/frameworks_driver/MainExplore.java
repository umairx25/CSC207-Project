package frameworks_driver;

import javax.swing.*;
import java.awt.*;

public class MainExplore {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Explore Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 600);

        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\shahe\\IdeaProjects\\CSC207-Project\\src\\main\\java\\frameworks_driver\\Adobe_Photoshop_CC_icon.png");
        frame.setIconImage(icon);
        ExplorePage explorePage = new ExplorePage();

        frame.add(explorePage);
        frame.setVisible(true);
    }
}
