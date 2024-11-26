//package interface_adapter.chart;
//
//import interface_adapter.ViewManagerModel;
//import use_case.chart.ChartOutputBoundary;
//import use_case.chart.ChartOutputData;
//
//public class ChartPresenter implements ChartOutputBoundary {
//
//    private final ChartViewModel viewModel;
//    private final ViewManagerModel viewManagerModel;
//
//    public ChartPresenter(ViewManagerModel viewManagerModel, ChartViewModel viewModel) {
//        this.viewManagerModel = viewManagerModel;
//        this.viewModel = viewModel;
//    }
//
//    @Override
//    public void presentChartData(ChartOutputData outputData) {
//
//        if (outputData.getEmaData()){
//            viewModel.updateEma(outputData.getEmaData());
//        }
//
//
//        viewModel.updateRsi(outputData.getRsiData());
//        viewModel.updatePriceHistory(outputData.getPriceHistory());
//        viewModel.updateSma(outputData.getSmaData());
//    }
//}

package interface_adapter.chart;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import use_case.chart.ChartOutputBoundary;
import use_case.chart.ChartOutputData;

import java.util.Map;

public class
ChartPresenter implements ChartOutputBoundary {
    private ChartViewModel viewModel;
    private ChartState chartState;

    public ChartPresenter(ChartViewModel viewModel) {
        this.viewModel = viewModel;
    }


//    private JFreeChart refreshChart(String title) {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        if (priceHistoryCheckBox.isSelected()) {
//            for (Map.Entry<Long, Double> entry : priceHistory.entrySet()) {
//                dataset.addValue(entry.getValue(), "Price History", entry.getKey());
//            }
//        }
//
//        if (smaCheckBox.isSelected()) {
//            for (Map.Entry<Long, Double> entry : smaData.entrySet()) {
//                dataset.addValue(entry.getValue(), "SMA", entry.getKey());
//            }
//        }
//
//        if (emaCheckBox.isSelected()) {
//            for (Map.Entry<Long, Double> entry : emaData.entrySet()) {
//                dataset.addValue(entry.getValue(), "EMA", entry.getKey());
//            }
//        }
//
//        if (rsiCheckBox.isSelected()) {
//            for (Map.Entry<Long, Double> entry : rsiData.entrySet()) {
//                dataset.addValue(entry.getValue(), "RSI", entry.getKey());
//            }
//        }
//
//        CategoryPlot plot = ((CategoryPlot) ((ChartPanel) getComponent(1)).getChart().getPlot());
//        plot.setDataset(dataset);
//        return plot.getChart();
//    }

    @Override
    public void presentChartData(ChartOutputData outputData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
//            if (outputData.getEmaData().containsKey(0L) && outputData.getEmaData().get(0L) == 0.0) {
//                //do nothing
//                System.out.println("Not selected");
//            }
//
//            else{
//                viewModel.updateEma(outputData.getEmaData());
//                chartState.setEma(outputData.getEmaData());
//            }
//
//            if (outputData.getSmaData().containsKey(0L) && outputData.getSmaData().get(0L) != 0.0) {
//                viewModel.updateSma(outputData.getSmaData());
//                chartState.setSma(outputData.getSmaData());
//            }
//
//            if (outputData.getRsiData().containsKey(0L) && outputData.getRsiData().get(0L) != 0.0) {
//                viewModel.updateRsi(outputData.getRsiData());
//                chartState.setRsi(outputData.getRsiData());
//            }
//
//            if (outputData.getPriceHistory().containsKey(0L) && outputData.getPriceHistory().get(0L) != 0.0) {
//                viewModel.updatePriceHistory(outputData.getPriceHistory());
//                chartState.setPriceHistory(outputData.getPriceHistory());
//            }
//
        viewModel.updatePriceHistory(outputData.getPriceHistory());
        viewModel.updateSma(outputData.getSmaData());
        viewModel.updateEma(outputData.getEmaData());
        viewModel.updateRsi(outputData.getRsiData());
            // Notify the UI to refresh
//        viewModel.updateView();
        }
        catch (Exception e) {
            System.out.println("Presenter: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
