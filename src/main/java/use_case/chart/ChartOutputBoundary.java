package use_case.chart;

/**
 * Output boundary for the Chart Use Case.
 * This interface is used to present chart data to the view.
 */
public interface ChartOutputBoundary {

    /**
     * Presents the chart data to the view.
     * This method is called by the interactor to pass the processed chart data
     * to the presenter, which will update the view accordingly.
     *
     * @param outputData The data to be presented, including chart information and calculated values.
     */
    void presentChartData(ChartOutputData outputData);
}