package app;

import java.awt.CardLayout;

import javax.swing.*;

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
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ChartViewModel chartViewModel = new ChartViewModel();
    private final StockDataAccess stockDataAccess = new StockDataAccess();
    final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(chartViewModel);
    final ChartInputBoundary chartInteractor = new ChartInteractor(stockDataAccess,
            (ChartPresenter) chartOutputBoundary);
    final ChartController controller = new ChartController(chartInteractor);
    ChartView chartView= new ChartView(chartViewModel, controller, chartViewModel.getState());

    public Builder() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        cardPanel.setLayout(cardLayout);
    }


    public Builder addChartView() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ChartViewModel chartViewModel = new ChartViewModel();

        // Initialize and set the SignupController
        final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(
                chartViewModel);
        final ChartInputBoundary chartInteractor = new ChartInteractor(
                stockDataAccess, (ChartPresenter) chartOutputBoundary);
        final ChartController controller = new ChartController(chartInteractor);

        ChartView chartView= new ChartView(chartViewModel, controller, chartViewModel.getState());

        cardPanel.add(chartView, chartView.getViewName());

        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Stock Flow");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(chartView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}