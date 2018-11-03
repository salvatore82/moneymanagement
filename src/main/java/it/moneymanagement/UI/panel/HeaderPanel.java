/**
 * 
 */
package it.moneymanagement.UI.panel;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HeaderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel headerIcon;
	
	public HeaderPanel() {
	}

	public void initComponents() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		headerIcon = new JLabel(" ");
		add(new JLabel(new ImageIcon("resources/images/header_l.png")));
		add(getHeaderIcon());
		add(new JLabel(new ImageIcon("resources/images/header_r.png")));
		setBackground(Color.WHITE);
		//setPreferredSize(new Dimension(0, 90));
	}

	public JLabel getHeaderIcon() {
		return headerIcon;
	}

	public void setHeaderIcon(JLabel headerIcon) {
		this.headerIcon = headerIcon;
	}

}
