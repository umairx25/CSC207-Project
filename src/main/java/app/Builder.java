package app;

import java.awt.*;

import javax.swing.*;

import data_access.explore.ExploreDataAccess;
import frameworks_driver.explore.view.ExploreView;
import interface_adapter.explore.ExploreController;
import interface_adapter.explore.ExplorePresenter;
import interface_adapter.explore.ExploreViewModel;
import use_case.explore.ExploreInputBoundary;
import use_case.explore.ExploreInteractor;
import use_case.explore.ExploreOutputBoundary;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */

public class Builder {
    private final JPanel cardPanel = new JPanel();
    private final ExploreViewModel exploreViewModel = new ExploreViewModel();
    private final ExploreDataAccess stockDataAccess = new ExploreDataAccess();
    final ExploreOutputBoundary exploreOutputBoundary = new ExplorePresenter(exploreViewModel);
    final ExploreInputBoundary exploreInteractor = new ExploreInteractor(stockDataAccess, exploreOutputBoundary);
    final ExploreController controller = new ExploreController(exploreInteractor);
    ExploreView exploreView = new ExploreView(controller, exploreViewModel);

    public Builder() {
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
    }


    public Builder addExploreView () {
        cardPanel.add(exploreView);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Stock Flow");
        application.setSize(1300, 600);
        Image icon = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        application.setIconImage(icon);

        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

//        viewManagerModel.setState(ExploreView.getViewName());
//        viewManagerModel.firePropertyChanged();

        return application;
    }
}