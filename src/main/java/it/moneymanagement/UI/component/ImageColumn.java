package it.moneymanagement.UI.component;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class ImageColumn extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor {
	private static final long serialVersionUID = 1L;

	private JButton renderButton;
	private JButton editButton;
	
	private int columnWithPath;

	public ImageColumn(JTable table, int column, int columnWithPath) {
		this.columnWithPath = columnWithPath;
		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted(false);

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		editButton.setIcon(new ImageIcon((String) table.getModel().getValueAt(
				row, columnWithPath)));
		editButton.setUI(new BasicButtonUI());
		return editButton;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		renderButton.setIcon(new ImageIcon((String) table.getModel()
				.getValueAt(row, columnWithPath)));
		renderButton.setUI(new BasicButtonUI());
		renderButton.setForeground(table.getForeground());
		renderButton.setBackground(table.getBackground());
		return renderButton;
	}
	
	public Object getCellEditorValue() {
		return null;
	}

}
