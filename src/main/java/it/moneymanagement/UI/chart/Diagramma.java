package it.moneymanagement.UI.chart;

import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.util.Rotation;

public class Diagramma extends ApplicationFrame {

	private static final long serialVersionUID = 1L;
	
	public Diagramma() {
		super("");
	}
	
	public void settaDiagramma(final String frameTitle, final String innerTitle, final Hashtable<String, Double> dati) {
		setTitle(frameTitle);
		// create a dataset...
		final PieDataset dataset = createSampleDataset(dati);
		// create the chart...
		final JFreeChart chart = createChart(dataset,innerTitle);
		// add the chart to a panel...
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
		setResizable(Boolean.FALSE);
	}
	
	public PieDataset createSampleDataset(final Hashtable<String, Double> dati) {
		DefaultPieDataset result = new DefaultPieDataset();
		Enumeration<String> chiavi = dati.keys();
		while (chiavi.hasMoreElements()) {
			String chiave = (String) chiavi.nextElement();
			Double valore = dati.get(chiave);
			result.setValue(chiave, valore);
		}
		return result;
	}

	private JFreeChart createChart(final PieDataset dataset, final String title) {
		final JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		final PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		plot.setNoDataMessage("Non ci sono dati.");
		return chart;
	}
	
	@Override
	public void windowClosing(WindowEvent event) {
		if (event.getWindow() == this) {
			dispose();
		}
	}
}