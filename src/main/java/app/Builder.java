package app;

import java.awt.*;

import javax.swing.*;

import data_access.PolygonDao;
import view.ExploreView;
import interface_adapter.ViewManagerModel;
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
    private final ExploreViewModel ExploreViewModel = new ExploreViewModel();
    private final PolygonDao stockDataAccess = new PolygonDao();
    final ExploreOutputBoundary ExploreOutputBoundary = new ExplorePresenter(ExploreViewModel);
    final ExploreInputBoundary ExploreInteractor = new ExploreInteractor(stockDataAccess, (ExplorePresenter) ExploreOutputBoundary);
    final ExploreController controller = new ExploreController(ExploreInteractor);
    ExploreView ExploreView= new ExploreView(controller, ExploreViewModel);

    public Builder() {
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Stock Flow");
        application.setSize(1300, 600);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\shahe\\IdeaProjects\\CSC207-Project\\images\\stock_flow_logo (1).png");
        application.setIconImage(icon);

        cardPanel.add(ExploreView);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

//        viewManagerModel.setState(ExploreView.getViewName());
//        viewManagerModel.firePropertyChanged();

        return application;
    }
}