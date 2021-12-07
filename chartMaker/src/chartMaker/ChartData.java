package chartMaker;

import java.awt.*;
import javax.swing.*;
import org.apache.commons.math3.stat.descriptive.*;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartData extends JPanel{
	
	public ChartData(String chartTitle, double[] vals) {
		JFreeChart line = ChartFactory.createLineChart(chartTitle, "X", "Y", createSet(vals));
		ChartPanel panel = new ChartPanel(line);
		panel.setPreferredSize(new Dimension(500, 500));
		
		this.add(panel);
	}
	
	static int y = 0;
	static double saltRange = 100;
	
	public static int funcSolve(int x) {
		
		y = (int)(Math.pow(x, 2) + x); // equation x^2 + x
		return y;
	}
	
	public static double[] dataPlot() {
		double data[] = new double[100];
		for(int i=1; i<101; i++) {
			data[i-1] = funcSolve(i);
		}
		
		return data;
	}
	
	public static double[] dataSalt(double[] vals) {
		double salty[] = new double[100];
		double data[] = vals;
		double rangeHalf = saltRange/2;
		for(int i=0; i<100; i++) {
			salty[i] = (data[i]-rangeHalf) + (Math.random()*saltRange);
		}
		return salty;
	}
	
	public static double[] rollingAvg(double[] vals) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		stats.setWindowSize(3);
		double averaged[] = new double[100];
		for(int i=0; i<100; i++) {
			stats.addValue(vals[i]);
			averaged[i] = stats.getMean();
		}
		
		return averaged;
	}
	
	public static DefaultCategoryDataset createSet(double[] vals) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0; i<100; i++) {
			dataset.addValue(vals[i], "x^2 + x", i+1+"");
		}
		return dataset;
	}
	
	public static double[] trunc(double[] vals) {
		double out[] = new double[100];
		for (int i=0; i<vals.length; i++) {
			out[i] = Math.floor(vals[i] * 100) / 100;
		}
		return out;
	}
	
	public static void plotAll() {
		double[] points = dataPlot();
		double[] saltedPts = dataSalt(points);
		double[] smoothPts1 = rollingAvg(saltedPts);
		double[] smoothPts2 = rollingAvg(smoothPts1);
		
		double[] truncSalt = trunc(saltedPts);
		
		System.out.print("[");
		for (int i=0; i<points.length-1; i++) {
			System.out.print(truncSalt[i] + ", ");
		}
		System.out.print(truncSalt[truncSalt.length-1]);
		System.out.print("]");
		
		/*JFrame frame = new JFrame("Charts");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(2, 2));
		frame.add(new ChartData("x^2 + x", points));
		frame.add(new ChartData("Salted", saltedPts));
		frame.add(new ChartData("Smoothed", smoothPts1));
		frame.add(new ChartData("Smoothed 2", smoothPts2));
		frame.pack();
		frame.setSize(1000, 1000);
		frame.setVisible(true);*/
	}
	
	public static void main(String[] args) {
		plotAll();
	}
	
}
