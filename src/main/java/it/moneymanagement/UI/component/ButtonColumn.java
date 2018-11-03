package it.moneymanagement.UI.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class ButtonColumn extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private Action action;
	private int mnemonic;
	private Border originalBorder;
	private Border focusBorder;

	private JButton renderButton;
	private JButton editButton;
	private String text;
	private boolean isButtonColumnEditor;

	public ButtonColumn(JTable table, Action action, int column, ImageIcon icon) {
		this.table = table;
		this.action = action;

		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted(false);
		editButton.addActionListener(this);
		if (icon != null) {
			renderButton.setIcon(icon);
			editButton.setIcon(icon);
			renderButton.setUI(new BasicButtonUI());
			editButton.setUI(new BasicButtonUI());
		} else {
			originalBorder = editButton.getBorder();
		}

		setFocusBorder(new LineBorder(Color.BLACK));

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
		table.addMouseListener(this);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		text = (value == null) ? "" : value.toString();
		editButton.setText(text);
		return editButton;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(table.getSelectionBackground());
		} else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(table.getBackground());
		}

		if (hasFocus) {
			renderButton.setBorder(focusBorder);
		} else {
			renderButton.setBorder(originalBorder);
		}

		renderButton.setText((value == null) ? "" : value.toString());
		return renderButton;
	}

	public Object getCellEditorValue() {
		return text;
	}
	
	public void actionPerformed(ActionEvent e) {
		int row = table.convertRowIndexToModel(table.getEditingRow());
		fireEditingStopped();
		ActionEvent event = new ActionEvent(table,
				ActionEvent.ACTION_PERFORMED, "" + row);
		action.actionPerformed(event);
	}

	public Border getFocusBorder() {
		return focusBorder;
	}

	public void setFocusBorder(Border focusBorder) {
		this.focusBorder = focusBorder;
		editButton.setBorder(focusBorder);
	}

	public int getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(int mnemonic) {
		this.mnemonic = mnemonic;
		renderButton.setMnemonic(mnemonic);
		editButton.setMnemonic(mnemonic);
	}
	
	public void mousePressed(MouseEvent e) {
		if (table.isEditing() && table.getCellEditor() == this)
			isButtonColumnEditor = true;
	}

	public void mouseReleased(MouseEvent e) {
		if (isButtonColumnEditor && table.isEditing())
			table.getCellEditor().stopCellEditing();

		isButtonColumnEditor = false;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
