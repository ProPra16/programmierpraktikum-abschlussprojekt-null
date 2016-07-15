package gui.views.statistics;

import javafx.geometry.Insets;
import javafx.scene.Node;
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
	private LineChart<Number, Number> createLineChart() {
		NumberAxis modeAxis = new NumberAxis();
		modeAxis.setLabel("Mode");
		NumberAxis timeAxis = new NumberAxis();
		timeAxis.setLabel("Time");
		
		LineChart<Number, Number> chart = new LineChart<Number, Number>(modeAxis, timeAxis);
		chart.setMinHeight(chartHeight);
		chart.setTitle("History");
		chart.setLegendVisible(false);
		chart.getStyleClass().add("statistic-chart");
		chart.getStyleClass().add("statistic-chart-line");
		
		
		XYChart.Series<Number, Number> trackingSeriesRed = new XYChart.Series<Number, Number>();
		XYChart.Series<Number, Number> trackingSeriesGreen = new XYChart.Series<Number, Number>();
		XYChart.Series<Number, Number> trackingSeriesBlue = new XYChart.Series<Number, Number>();
		
		for(int i = 0; i < trackingSession.getData().size(); i++) {
			TrackingData trackingData = trackingSession.getData().get(i); 
			XYChart.Data<Number, Number> data = new XYChart.Data<Number, Number>(i+1, trackingData.getDuration()/1000.0);
			
			switch(trackingData.getMode()) {
			case RED:
				trackingSeriesRed.getData().add(data);
				break;
			case GREEN:
				trackingSeriesGreen.getData().add(data);
				break;
			case BLUE:
				trackingSeriesBlue.getData().add(data);
				break;
			}
		}
		
		chart.getData().add(trackingSeriesRed);
		chart.getData().add(trackingSeriesGreen);
		chart.getData().add(trackingSeriesBlue);
		
		return chart;
	}
	
	/** 
	 * Creates a pie chart
	 * 
	 * @return {@link PieChart}
	 */
	private PieChart createPieChart() {
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
		
		if(durations[0] != 0) {
			chart.getData().addAll(redData, greenData, blueData);
		}
		
		return chart;
	}

}
