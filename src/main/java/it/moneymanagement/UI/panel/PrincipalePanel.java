/**
 * 
 */
package it.moneymanagement.UI.panel;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PrincipalePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private HeaderPanel headerPanel;
	private ContentPanel contentPanel;

	public PrincipalePanel() {
	}

	public void initComponents() {
		setLayout(new BorderLayout());
		getHeaderPanel().getHeaderIcon().setIcon(new ImageIcon("resources/images/mainlogo.png"));
		add(getHeaderPanel(), BorderLayout.NORTH);
		add(getContentPanel(), BorderLayout.CENTER);
	}

	public HeaderPanel getHeaderPanel() {
		return headerPanel;
	}

	public void setHeaderPanel(HeaderPanel headerPanel) {
		this.headerPanel = headerPanel;
	}

	public ContentPanel getContentPanel() {
		return contentPanel;
	}

	public void setContentPanel(ContentPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

}
