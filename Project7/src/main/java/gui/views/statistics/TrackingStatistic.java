package gui.views.statistics;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.TrackingData;
import models.TrackingSession;

public class TrackingStatistic extends VBox {
	
	final int chartHeight = 400;
	TrackingSession trackingSession;
	
	/**
	 * Constructs a tracking statistic from a tracking session
	 * 
	 * @param trackingSession
	 */
	public TrackingStatistic(TrackingSession trackingSession) {
		this.trackingSession = trackingSession;
		
		getStyleClass().add("statistic");
		setFillWidth(true);
		
		Label header = new Label(trackingSession.getExerciseName());
		header.getStyleClass().add("statistic-header");
		
		Label date = new Label(trackingSession.getStartDate().toString());
		date.getStyleClass().add("statistic-date");
		VBox.setMargin(date, new Insets(0, 0, 40, 0));
		
		Node lineChart = createLineChart(); 
		VBox.setMargin(lineChart, new Insets(0, 0, 40, 0));
		
		Node pieChart = createPieChart();
		
		getChildren().addAll(header, date, lineChart, pieChart);
	}
	
	/**
	 * Creates a line chart
	 * 
	 * @return {@link LineChart}
	 */
	private Node createLineChart() {
		CategoryAxis modeAxis = new CategoryAxis();
		modeAxis.setLabel("Mode");
		NumberAxis timeAxis = new NumberAxis();
		timeAxis.setLabel("Time");
		
		LineChart<String, Number> chart = new LineChart<String, Number>(modeAxis, timeAxis);
		chart.setMinHeight(chartHeight);
		chart.setTitle("History");
		chart.setLegendVisible(false);
		chart.getStyleClass().add("statistic-chart");
		chart.getStyleClass().add("statistic-chart-line");
		
		XYChart.Series<String, Number> trackingSeries = new XYChart.Series<String, Number>();
		for(TrackingData trackingData : trackingSession.getData()) {
			XYChart.Data<String, Number> data = new XYChart.Data<String, Number>(trackingData.getModeString(), trackingData.getDuration()/1000.0);
			trackingSeries.getData().add(data);
		}
		chart.getData().add(trackingSeries);
		
		return chart;
	}
	
	/** 
	 * Creates a pie chart
	 */
	private Node createPieChart() {
		PieChart chart = new PieChart();
		chart.setMinHeight(chartHeight);
		chart.setTitle("Time needed in mode");
		chart.setLegendVisible(false);
		chart.getStyleClass().add("statistic-chart");
		chart.getStyleClass().add("statistic-chart-pie");
		
		long[] durations = trackingSession.getDuration();
			
		PieChart.Data redData = new PieChart.Data("RED", durations[0]/1000.0);
		PieChart.Data greenData = new PieChart.Data("GREEN", durations[1]/1000.0);
		PieChart.Data blueData = new PieChart.Data("BLUE", durations[2]/1000.0);
		
		chart.getData().addAll(redData, greenData, blueData);
		
		/* Not needed - already overrode the default colors
		redData.getNode().getStyleClass().add("red");
		greenData.getNode().getStyleClass().add("green");
		blueData.getNode().getStyleClass().add("blue");
		*/
		return chart;
	}

}
