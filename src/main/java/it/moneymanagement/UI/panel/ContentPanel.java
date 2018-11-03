/**
 * 
 */
package it.moneymanagement.UI.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContentPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ContentPanel() {
	}

	public void initComponents() {
		setLayout(new BorderLayout());
		JLabel picLabel = new JLabel(new ImageIcon("resources/images/europoker.jpg"));
		add(picLabel,BorderLayout.CENTER);
		setBackground(Color.WHITE);
	}
}
