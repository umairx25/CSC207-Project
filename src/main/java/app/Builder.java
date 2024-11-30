package app;

import java.awt.*;
import javax.swing.*;

// Explore
import data_access.explore.ExploreDataAccess;
import frameworks_driver.view.explore.ExploreView;
import interface_adapter.explore.ExploreController;
import interface_adapter.explore.ExplorePresenter;
import interface_adapter.explore.ExploreViewModel;
import use_case.explore.ExploreInputBoundary;
import use_case.explore.ExploreInteractor;
import use_case.explore.ExploreOutputBoundary;

// Chart
import data_access.chart.StockDataAccess;
import frameworks_driver.view.chart.ChartView;
import interface_adapter.ViewManagerModel;
import interface_adapter.chart.ChartController;
import interface_adapter.chart.ChartPresenter;
import interface_adapter.chart.ChartViewModel;
import use_case.chart.ChartInputBoundary;
import use_case.chart.ChartInteractor;
import use_case.chart.ChartOutputBoundary;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */

public class Builder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    // Chart
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ChartViewModel chartViewModel = new ChartViewModel();
    private final StockDataAccess stockDataAccess = new StockDataAccess();
    final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(chartViewModel);
    final ChartInputBoundary chartInteractor = new ChartInteractor(stockDataAccess,
            (ChartPresenter) chartOutputBoundary);
    final ChartController chartController = new ChartController(chartInteractor);
    ChartView chartView = new ChartView(chartViewModel, chartController, chartViewModel.getState());

    // Explore
    private final ExploreViewModel exploreViewModel = new ExploreViewModel();
    private final ExploreDataAccess exploreDataAccess = new ExploreDataAccess();
    final ExploreOutputBoundary exploreOutputBoundary = new ExplorePresenter(exploreViewModel);
    final ExploreInputBoundary exploreInteractor = new ExploreInteractor(exploreDataAccess, exploreOutputBoundary);
    final ExploreController exploreController = new ExploreController(exploreInteractor);
    ExploreView exploreView = new ExploreView(exploreController, exploreViewModel, chartView);

    public Builder() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        cardPanel.setLayout(cardLayout);
    }

    public Builder addExploreView() {
        cardPanel.add(exploreView);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     *
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Stock Flow");
//        application.setSize(1300, 800);
        application.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Image icon = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        application.setIconImage(icon);

        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(chartView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}