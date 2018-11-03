package it.moneymanagement.UI.frame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.jfree.ui.RefineryUtilities;

public class SplashScreen extends JWindow {

	private static final long serialVersionUID = 1L;

	public SplashScreen() {
	}

	public void showSplash() {
		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(Color.WHITE);
		labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		labelPanel.add(new JLabel(new ImageIcon("resources/images/header_l.png")));
		labelPanel.add(new JLabel(new ImageIcon("resources/images/loading.png")));
		labelPanel.add(new JLabel(new ImageIcon("resources/images/header_r.png")));
		labelPanel.setBorder(BorderFactory.createEmptyBorder(180, 0, 0, 0));
		labelPanel.setSize(new Dimension(1015, 665));
		getContentPane().add(labelPanel);  
		
		setSize(new Dimension(1000, 650));
		RefineryUtilities.centerFrameOnScreen(this);
		
		setVisible(true);
	}
}